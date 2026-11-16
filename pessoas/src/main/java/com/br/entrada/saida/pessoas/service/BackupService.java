package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupService {

    public record ResultadoArquivamento(int ano,
                                        boolean apagou,
                                        int registrosCopiados,
                                        int pessoasNoBackup,
                                        int pacientesInternadosNoBackup,
                                        int acompanhantesCopiados,
                                        int logsCopiados,
                                        int registrosApagados,
                                        int pessoasApagadas,
                                        int pacientesInternadosApagados,
                                        int acompanhantesApagados) {}

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    private static final Pattern DUMP_DE_SERVIDOR = Pattern.compile(
            "^\\s*(CREATE\\s+DATABASE|DROP\\s+DATABASE|--\\s*Current Database:)", Pattern.CASE_INSENSITIVE);

    private static final Pattern COMANDO_DE_TABELA_DE_LOG = Pattern.compile(
            "^(/\\*!\\d+\\s+)?(DROP TABLE|CREATE TABLE|LOCK TABLES|ALTER TABLE|INSERT INTO)\\s+"
                    + "(IF NOT EXISTS\\s+|IF EXISTS\\s+)?`?LOG", Pattern.CASE_INSENSITIVE);

    public ResultadoArquivamento arquivarAno(int ano, boolean apagar) {
        if (ano < 2000 || ano > 2100) {
            throw new RegraNegocioException("Ano inválido", "Informe um ano válido entre 2000 e 2100.");
        }

        String tReg = "registros_" + ano;
        String tPes = "pessoas_" + ano;
        String tLog = "logs_" + ano;
        String tPacInt = "pacientes_internados_" + ano;
        String tAcomp = "acompanhantes_" + ano;

        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tReg + " LIKE registros");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tPes + " LIKE pessoas");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tLog + " LIKE log_auditoria");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tPacInt + " LIKE pacientes_internados");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tAcomp + " LIKE acompanhantes");
        } catch (DataAccessException e) {
            log.error("Erro ao criar tabelas de backup para o ano {}", ano, e);
            throw new RegraNegocioException("Falha na estrutura de backup",
                    "Não foi possível preparar as tabelas de backup para o ano " + ano + ". Nada foi alterado.");
        }

        List<String> colsReg = colunasComuns("registros", tReg);
        List<String> colsPes = colunasComuns("pessoas", tPes);
        List<String> colsLog = colunasComuns("log_auditoria", tLog);
        List<String> colsPacInt = colunasComuns("pacientes_internados", tPacInt);
        List<String> colsAcomp = colunasComuns("acompanhantes", tAcomp);

        Timestamp inicio = Timestamp.valueOf(LocalDateTime.of(ano, 1, 1, 0, 0));
        Timestamp fim = Timestamp.valueOf(LocalDateTime.of(ano + 1, 1, 1, 0, 0));

        try {
            ResultadoArquivamento resultado = new TransactionTemplate(transactionManager).execute(status -> {

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

                // 3) Pacientes Internados (atualiza todos no backup para manter integridade de FK com acompanhantes)
                jdbcTemplate.update("DELETE d FROM " + tPacInt + " d JOIN pacientes_internados p ON p.id = d.id");
                int pacientesInternadosNoBackup = jdbcTemplate.update(
                        "INSERT INTO " + tPacInt + " (" + listar(colsPacInt, null) + ") " +
                                "SELECT " + listar(colsPacInt, null) + " FROM pacientes_internados");

                // 4) Acompanhantes fechados do ano
                int acompanhantesCopiados = jdbcTemplate.update(
                        "INSERT INTO " + tAcomp + " (" + listar(colsAcomp, null) + ") " +
                                "SELECT " + listar(colsAcomp, "a") + " FROM acompanhantes a " +
                                "WHERE a.hora_saida IS NOT NULL " +
                                "AND a.hora_entrada >= ? AND a.hora_entrada < ? " +
                                "AND NOT EXISTS (SELECT 1 FROM " + tAcomp + " d WHERE d.id = a.id)",
                        inicio, fim);

                // 5) Logs de Auditoria (inclui todos os módulos: PESSOA, USUARIO, PACIENTE_INTERNADO, ACOMPANHANTE, etc.)
                int logsCopiados = jdbcTemplate.update(
                        "INSERT INTO " + tLog + " (" + listar(colsLog, null) + ") " +
                                "SELECT " + listar(colsLog, "l") + " FROM log_auditoria l " +
                                "WHERE l.data_hora >= ? AND l.data_hora < ? " +
                                "AND NOT EXISTS (SELECT 1 FROM " + tLog + " dl WHERE dl.id = l.id)",
                        inicio, fim);

                int registrosApagados = 0;
                int pessoasApagadas = 0;
                int acompanhantesApagados = 0;
                int pacientesInternadosApagados = 0;

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

                    // Remove acompanhantes já arquivados e fechados do ano
                    acompanhantesApagados = jdbcTemplate.update(
                            "DELETE a FROM acompanhantes a JOIN " + tAcomp + " d ON d.id = a.id " +
                                    "WHERE a.hora_saida IS NOT NULL " +
                                    "AND a.hora_entrada >= ? AND a.hora_entrada < ?",
                            inicio, fim);

                    // Remove pacientes internados já encerrados no ano que não têm mais acompanhantes ativos/pendentes
                    pacientesInternadosApagados = jdbcTemplate.update(
                            "DELETE p FROM pacientes_internados p JOIN " + tPacInt + " dp ON dp.id = p.id " +
                                    "WHERE p.hora_saida IS NOT NULL " +
                                    "AND p.hora_entrada >= ? AND p.hora_entrada < ? " +
                                    "AND NOT EXISTS (SELECT 1 FROM acompanhantes a WHERE a.paciente_id = p.id)",
                            inicio, fim);
                }

                return new ResultadoArquivamento(ano, apagar,
                        registrosCopiados, pessoasNoBackup,
                        pacientesInternadosNoBackup, acompanhantesCopiados,
                        logsCopiados,
                        registrosApagados, pessoasApagadas,
                        pacientesInternadosApagados, acompanhantesApagados);
            });

            if (resultado == null) {
                throw new RegraNegocioException("Falha no arquivamento",
                        "Não foi possível concluir a transação de arquivamento para o ano " + ano + ".");
            }

            return resultado;
        } catch (RegraNegocioException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro transacional ao arquivar o ano {} (apagar={})", ano, apagar, e);
            throw new RegraNegocioException("Falha ao arquivar",
                    "Ocorreu um erro no banco de dados durante o arquivamento do ano " + ano + ". Nada foi apagado.");
        }
    }

    public boolean verificarExistenciaBackup(int ano) {
        if (ano < 2000 || ano > 2100) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME IN (?, ?, ?, ?, ?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                "registros_" + ano,
                "pessoas_" + ano,
                "logs_" + ano,
                "pacientes_internados_" + ano,
                "acompanhantes_" + ano);
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
            throw new RegraNegocioException("Estrutura incompatível",
                    "Estrutura inesperada nas tabelas " + origem + " / " + destino + ".");
        }
        return cols;
    }

    private static String listar(List<String> cols, String alias) {
        return cols.stream()
                .map(c -> (alias == null ? "" : alias + ".") + "`" + c + "`")
                .collect(Collectors.joining(", "));
    }

    public void importarSql(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new RegraNegocioException("Arquivo obrigatório", "Nenhum arquivo foi selecionado para importação.");
        }

        String nome = arquivo.getOriginalFilename();
        if (nome == null || !nome.toLowerCase().endsWith(".sql")) {
            throw new RegraNegocioException("Formato inválido", "O arquivo precisa ter a extensão .sql.");
        }

        StringBuilder sqlLimpo = new StringBuilder();

        try (InputStream in = arquivo.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            String linha;
            boolean ignorarBloco = false;

            while ((linha = reader.readLine()) != null) {
                String t = linha.trim();

                if (DUMP_DE_SERVIDOR.matcher(t).find()) {
                    throw new RegraNegocioException("Dump não suportado",
                            "Este arquivo é um dump de servidor e não pode ser importado por esta tela. " +
                                    "Use o arquivo backup_<banco>_<data>.sql. Nada foi alterado no banco.");
                }

                if (t.regionMatches(true, 0, "DELIMITER", 0, 9)) {
                    throw new RegraNegocioException("Backup incompatível",
                            "Este backup contém rotinas/triggers/eventos (DELIMITER), que esta tela não consegue restaurar. " +
                                    "Gere o backup com o backup-banco.bat/.sh atualizado ou restaure por linha de comando. Nada foi alterado no banco.");
                }

                if (COMANDO_DE_TABELA_DE_LOG.matcher(t).find()) {
                    ignorarBloco = true;
                }
                if (ignorarBloco) {
                    if (t.endsWith(";")) {
                        ignorarBloco = false;
                    }
                    continue;
                }

                if (t.regionMatches(true, 0, "INSERT INTO", 0, 11) && linha.contains("_binary")) {
                    throw new RegraNegocioException("Backup antigo incompatível",
                            "Este backup foi gerado sem --hex-blob (ids em _binary) e não pode ser importado com segurança. " +
                                    "Gere um backup novo com o backup-banco.bat/.sh atualizado e importe o novo arquivo. Nada foi alterado no banco.");
                }

                sqlLimpo.append(linha).append("\n");
            }
        } catch (RegraNegocioException e) {
            throw e;
        } catch (IOException e) {
            log.error("Erro de I/O ao ler o arquivo SQL '{}'", nome, e);
            throw new RegraNegocioException("Erro de leitura",
                    "Não foi possível ler o arquivo enviado. Verifique se ele não está corrompido. Nada foi alterado no banco.");
        }

        if (sqlLimpo.toString().isBlank()) {
            throw new RegraNegocioException("Arquivo SQL vazio",
                    "O arquivo enviado não contém comandos SQL válidos para restauração. Nada foi alterado no banco.");
        }

        EncodedResource script = new EncodedResource(
                new ByteArrayResource(sqlLimpo.toString().getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8
        );

        try {
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
                    } catch (SQLException ignored) {
                    }
                }
                return null;
            });
        } catch (DataAccessException e) {
            log.error("Erro ao executar script SQL de restauração a partir de '{}'", nome, e);
            throw new RegraNegocioException("Falha na restauração",
                    "Ocorreu um erro ao executar os comandos SQL no banco de dados. " +
                            "O banco pode estar parcialmente restaurado; refaça a importação com um arquivo válido.");
        }
    }
}