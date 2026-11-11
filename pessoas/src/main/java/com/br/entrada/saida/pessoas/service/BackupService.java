package com.br.entrada.saida.pessoas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Arquivamento anual (registros_AAAA / pessoas_AAAA) e importação de dump SQL.
 */
@Service
@RequiredArgsConstructor
public class BackupService {

    public record ResultadoArquivamento(int ano,
                                        boolean apagou,
                                        int registrosCopiados,
                                        int pessoasNoBackup,
                                        int registrosApagados,
                                        int pessoasApagadas) {}

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    // ==========================================================
    // ARQUIVAR (backup e manter / backup e apagar)
    // ==========================================================

    /**
     * Acrescenta nas tabelas do ano (registros_ANO e pessoas_ANO, criadas se não existirem):
     *  - registros FECHADOS (hora_saida preenchida) com hora_entrada dentro do ano, ainda não copiados;
     *  - todas as pessoas (a cópia da pessoa no backup é atualizada se já existir).
     * Se apagar = true, remove da base atual SOMENTE o que está comprovadamente no backup:
     *  - os registros arquivados;
     *  - as pessoas que tiveram registros arquivados e não têm mais nenhum registro na base atual.
     * Registros em aberto (pessoa ainda dentro) nunca são copiados nem apagados.
     */
    public ResultadoArquivamento arquivarAno(int ano, boolean apagar) {
        if (ano < 2000 || ano > 2100) {
            throw new IllegalArgumentException("Ano inválido: " + ano);
        }

        // 'ano' é int validado, então os nomes de tabela abaixo são seguros contra injeção.
        String tReg = "registros_" + ano;
        String tPes = "pessoas_" + ano;

        // DDL fora da transação: o MySQL faz commit implícito em CREATE TABLE.
        // LIKE copia chave primária e índices (e não copia chaves estrangeiras).
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tReg + " LIKE registros");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tPes + " LIKE pessoas");

        // Só colunas presentes nas duas tabelas (tolera tabela de ano antiga sem alguma coluna nova).
        List<String> colsReg = colunasComuns("registros", tReg);
        List<String> colsPes = colunasComuns("pessoas", tPes);

        Timestamp inicio = Timestamp.valueOf(LocalDateTime.of(ano, 1, 1, 0, 0));
        Timestamp fim = Timestamp.valueOf(LocalDateTime.of(ano + 1, 1, 1, 0, 0));

        return new TransactionTemplate(transactionManager).execute(status -> {

            // 1) Registros fechados do ano que ainda não estão no backup (NOT EXISTS evita duplicar,
            //    mesmo nas tabelas antigas criadas sem chave primária).
            int registrosCopiados = jdbcTemplate.update(
                    "INSERT INTO " + tReg + " (" + listar(colsReg, null) + ") " +
                            "SELECT " + listar(colsReg, "r") + " FROM registros r " +
                            "WHERE r.hora_saida IS NOT NULL " +
                            "AND r.hora_entrada >= ? AND r.hora_entrada < ? " +
                            "AND NOT EXISTS (SELECT 1 FROM " + tReg + " d WHERE d.id = r.id)",
                    inicio, fim);

            // 2) Pessoas: atualiza a cópia do backup (apaga a versão antiga e reinsere a atual).
            jdbcTemplate.update(
                    "DELETE d FROM " + tPes + " d JOIN pessoas p ON p.id = d.id");
            int pessoasNoBackup = jdbcTemplate.update(
                    "INSERT INTO " + tPes + " (" + listar(colsPes, null) + ") " +
                            "SELECT " + listar(colsPes, null) + " FROM pessoas");

            int registrosApagados = 0;
            int pessoasApagadas = 0;

            if (apagar) {
                // 3) Só apaga registros que EXISTEM no backup (JOIN com a tabela do ano).
                registrosApagados = jdbcTemplate.update(
                        "DELETE r FROM registros r JOIN " + tReg + " d ON d.id = r.id " +
                                "WHERE r.hora_saida IS NOT NULL " +
                                "AND r.hora_entrada >= ? AND r.hora_entrada < ?",
                        inicio, fim);

                // 4) Pessoas: só as que existem no backup, tiveram registros arquivados
                //    e não têm mais nenhum registro na base atual (inclui os em aberto).
                pessoasApagadas = jdbcTemplate.update(
                        "DELETE p FROM pessoas p JOIN " + tPes + " dp ON dp.id = p.id " +
                                "WHERE EXISTS (SELECT 1 FROM " + tReg + " dr WHERE dr.pessoa_id = p.id) " +
                                "AND NOT EXISTS (SELECT 1 FROM registros r WHERE r.pessoa_id = p.id)");
            }

            return new ResultadoArquivamento(ano, apagar, registrosCopiados, pessoasNoBackup,
                    registrosApagados, pessoasApagadas);
        });
    }

    private List<String> colunasComuns(String origem, String destino) {
        List<String> cols = jdbcTemplate.queryForList(
                "SELECT o.COLUMN_NAME FROM information_schema.COLUMNS o " +
                        "JOIN information_schema.COLUMNS d " +
                        "  ON d.TABLE_SCHEMA = o.TABLE_SCHEMA AND d.COLUMN_NAME = o.COLUMN_NAME AND d.TABLE_NAME = ? " +
                        "WHERE o.TABLE_SCHEMA = DATABASE() AND o.TABLE_NAME = ? " +
                        "ORDER BY o.ORDINAL_POSITION",
                String.class, destino, origem);
        if (cols.isEmpty() || !cols.contains("id")) {
            throw new IllegalStateException("Estrutura inesperada nas tabelas " + origem + " / " + destino);
        }
        return cols;
    }

    private static String listar(List<String> cols, String alias) {
        return cols.stream()
                .map(c -> (alias == null ? "" : alias + ".") + "`" + c + "`")
                .collect(Collectors.joining(", "));
    }

    // ==========================================================
    // IMPORTAR DUMP .sql (gerado por mysqldump)
    // ==========================================================

    /**
     * Executa o arquivo .sql na base atual. Um dump do mysqldump contém DROP TABLE + CREATE TABLE + INSERT,
     * então SUBSTITUI as tabelas existentes. DDL no MySQL não é transacional: se o arquivo falhar no meio,
     * a base fica parcialmente restaurada.
     */
    public void importarSql(MultipartFile arquivo) throws IOException {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo enviado.");
        }
        String nome = arquivo.getOriginalFilename();
        if (nome == null || !nome.toLowerCase().endsWith(".sql")) {
            throw new IllegalArgumentException("O arquivo precisa ter a extensão .sql.");
        }

        try (InputStream in = arquivo.getInputStream();
             java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(in, StandardCharsets.UTF_8))) {

            StringBuilder sqlLimpo = new StringBuilder();
            String linha;
            boolean ignorarBloco = false;

            while ((linha = reader.readLine()) != null) {
                String linhaUpper = linha.toUpperCase();

                // Detecta e ignora blocos de tabelas de log ou auditoria (ex: log_auditoria, logs_2025, logs_2026, etc.)
                if (linhaUpper.contains("TABLE `LOG") || linhaUpper.contains("TABLE LOG") ||
                        linhaUpper.contains("LOCK TABLES `LOG") || linhaUpper.contains("LOCK TABLES LOG") ||
                        linhaUpper.contains("INSERT INTO `LOG") || linhaUpper.contains("INSERT INTO LOG")) {
                    ignorarBloco = true;
                }

                // Se estiver dentro de um bloco de log, pula até o término do comando (ponto e vírgula)
                if (ignorarBloco) {
                    if (linha.trim().endsWith(";")) {
                        ignorarBloco = false;
                    }
                    continue;
                }

                // Remove linhas individuais ou dados binários soltos que venham de logs
                if (linha.contains("_binary") || (linha.contains("binary(16)") && linha.contains("usuario_cpf"))) {
                    continue;
                }

                sqlLimpo.append(linha).append("\n");
            }

            EncodedResource script = new EncodedResource(
                    new org.springframework.core.io.ByteArrayResource(sqlLimpo.toString().getBytes(StandardCharsets.UTF_8)),
                    StandardCharsets.UTF_8
            );

            jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
                try (Statement st = con.createStatement()) {
                    st.execute("SET FOREIGN_KEY_CHECKS=0");
                }
                try {
                    ScriptUtils.executeSqlScript(con, script, false, true,
                            ScriptUtils.DEFAULT_COMMENT_PREFIX,
                            ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
                            ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                            ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
                } finally {
                    try (Statement st = con.createStatement()) {
                        st.execute("SET FOREIGN_KEY_CHECKS=1");
                    } catch (SQLException ignored) {}
                }
                return null;
            });
        } catch (Exception e) {
            throw new IOException("Erro ao processar o arquivo de importação: " + e.getMessage(), e);
        }
    }
}