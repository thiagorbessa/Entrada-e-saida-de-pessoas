package com.br.entrada.saida.pessoas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupAutomaticoService {

    private final BackupService backupService;

    // Roda todo dia 1 de janeiro à 01:00 AM
    @Scheduled(cron = "0 0 1 1 1 ?")
    public void executarBackupAnual() {
        int anoAnterior = LocalDate.now().getYear() - 1;
        log.info("Iniciando o backup automático anual para o ano: {}", anoAnterior);

        try {
            BackupService.ResultadoArquivamento r = backupService.arquivarAno(anoAnterior, false);
            log.info("Backup automático de {} concluído! Registros: {}, Pessoas: {}, Pacientes Internados: {}, Acompanhantes: {}, Logs: {}",
                    anoAnterior, r.registrosCopiados(), r.pessoasNoBackup(),
                    r.pacientesInternadosNoBackup(), r.acompanhantesCopiados(), r.logsCopiados());
        } catch (Exception e) {
            log.error("Erro ao executar o backup automático do ano {}: {}", anoAnterior, e.getMessage(), e);
        }
    }
}