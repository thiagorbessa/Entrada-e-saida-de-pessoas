package com.br.entrada.saida.pessoas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupAutomaticoService {

    private final JdbcTemplate jdbcTemplate;

    // Roda todo dia 1 de janeiro à 01:00 AM
    @Scheduled(cron = "0 0 1 1 1 ?")
    public void executarBackupAnual() {
        int anoAtual = LocalDate.now().getYear();
        int anoAnterior = anoAtual - 1;

        String nomeTabelaBackup = "log_auditoria_" + anoAnterior;
        log.info("Iniciando o backup automático para a tabela: {}", nomeTabelaBackup);

        try {
            // 1. Cria a tabela de backup com os dados do ano anterior
            String sqlCreate = String.format("""
                CREATE TABLE IF NOT EXISTS %s AS 
                SELECT * FROM log_auditoria 
                WHERE YEAR(dataHora) = %d
            """, nomeTabelaBackup, anoAnterior);

            jdbcTemplate.execute(sqlCreate);

            // 2. Remove os dados antigos da tabela principal para manter a performance
            String sqlDelete = String.format("""
                DELETE FROM log_auditoria 
                WHERE YEAR(dataHora) = %d
            """, anoAnterior);

            jdbcTemplate.update(sqlDelete);

            log.info("Backup automático do ano {} concluído com sucesso!", anoAnterior);
        } catch (Exception e) {
            log.error("Erro ao executar o backup automático: {}", e.getMessage(), e);
        }
    }
}