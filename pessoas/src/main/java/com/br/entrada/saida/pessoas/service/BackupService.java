package com.br.entrada.saida.pessoas.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class BackupService {

    public record ResultadoArquivamento(int ano,
                                        boolean apagou,
                                        int registrosCopiados,
                                        int pessoasNoBackup,
                                        int logsCopiados,
                                        int registrosApagados,
                                        int pessoasApagadas) {}

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public ResultadoArquivamento arquivarAno(int ano, boolean apagar) {
        if (ano < 2000 || ano > 2100) {
            throw new IllegalArgumentException("Ano inválido: " + ano);
        }

        String tReg = "registros_" + ano;
        String tPes = "pessoas_" + ano;
        String tLog = "logs_" + ano; // Nome correto da tabela de backup de logs

        // DDL com os nomes corretos das tabelas do MySQL
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tReg + " LIKE registros");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tPes + " LIKE pessoas");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tLog + " LIKE log_auditoria");

        List<String> colsReg = colunasComuns("registros", tReg);
        List<String> colsPes = colunasComuns("pessoas", tPes);
        List<String> colsLog = colunasComuns("log_auditoria", tLog);

        Timestamp inicio = Timestamp.valueOf(LocalDateTime.of(ano, 1, 1, 0, 0));
        Timestamp fim = Timestamp.valueOf(LocalDateTime.of(ano + 1, 1, 1, 0, 0));

        return new TransactionTemplate(transactionManager).execute(status -> {

            // 1) Registros fechados do ano
            int registrosCopiados = jdbcTemplate.update(
                    "INSERT INTO " + tReg + " (" + listar(colsReg, null) + ") " +
                            "SELECT " + listar(colsReg, "r") + " FROM registros r " +
                            "WHERE r.hora_saida IS NOT NULL " +
                            "AND r.hora_entrada >= ? AND r.hora_entrada < ? " +
                            "AND NOT EXISTS (SELECT 1 FROM " + tReg + " d WHERE d.id = r.id)",
                    inicio, fim);

            // 2) Pessoas
            jdbcTemplate.update("DELETE d FROM " + tPes + " d JOIN pessoas p ON p.id = d.id");
            int pessoasNoBackup = jdbcTemplate.update(
                    "INSERT INTO " + tPes + " (" + listar(colsPes, null) + ") " +
                            "SELECT " + listar(colsPes, null) + " FROM pessoas");

            // 3) Logs de Auditoria (tabela real: log_auditoria)
            int logsCopiados = jdbcTemplate.update(
                    "INSERT INTO " + tLog + " (" + listar(colsLog, null) + ") " +
                            "SELECT " + listar(colsLog, "l") + " FROM log_auditoria l " +
                            "WHERE l.data_hora >= ? AND l.data_hora < ? " +
                            "AND NOT EXISTS (SELECT 1 FROM " + tLog + " dl WHERE dl.id = l.id)",
                    inicio, fim);

            int registrosApagados = 0;
            int pessoasApagadas = 0;

            if (apagar) {
                registrosApagados = jdbcTemplate.update(
                        "DELETE r FROM registros r JOIN " + tReg + " d ON d.id = r.id " +
                                "WHERE r.hora_saida IS NOT NULL " +
                                "AND r.hora_entrada >= ? AND r.hora_entrada < ?",
                        inicio, fim);

                pessoasApagadas = jdbcTemplate.update(
                        "DELETE p FROM pessoas p JOIN " + tPes + " dp ON dp.id = p.id " +
                                "WHERE EXISTS (SELECT 1 FROM " + tReg + " dr WHERE dr.pessoa_id = p.id) " +
                                "AND NOT EXISTS (SELECT 1 FROM registros r WHERE r.pessoa_id = p.id)");
            }

            return new ResultadoArquivamento(ano, apagar, registrosCopiados, pessoasNoBackup,
                    logsCopiados, registrosApagados, pessoasApagadas);
        });
    }

    public boolean verificarExistenciaBackup(int ano) {
        String tReg = "registros_" + ano;
        String tPes = "pessoas_" + ano;
        String tLog = "logs_" + ano;

        String sql = "SELECT COUNT(*) FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME IN (?, ?, ?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tReg, tPes, tLog);
        return count != null && count > 0;
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

                if (linhaUpper.contains("TABLE `LOG") || linhaUpper.contains("TABLE LOG") ||
                        linhaUpper.contains("LOCK TABLES `LOG") || linhaUpper.contains("LOCK TABLES LOG") ||
                        linhaUpper.contains("INSERT INTO `LOG") || linhaUpper.contains("INSERT INTO LOG")) {
                    ignorarBloco = true;
                }

                if (ignorarBloco) {
                    if (linha.trim().endsWith(";")) {
                        ignorarBloco = false;
                    }
                    continue;
                }

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