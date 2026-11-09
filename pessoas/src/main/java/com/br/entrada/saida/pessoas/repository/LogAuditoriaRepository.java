package com.br.entrada.saida.pessoas.repository;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {
    List<LogAuditoria> findAllByOrderByDataHoraDesc();

    // Versão paginada para os logs
    Page<LogAuditoria> findAllByOrderByDataHoraDesc(Pageable pageable);

    List<LogAuditoria> findByUsuarioCpfOrderByDataHoraDesc(String usuarioCpf);
    List<LogAuditoria> findByEntidadeIdOrderByDataHoraDesc(UUID entidadeId);
}