package com.br.entrada.saida.pessoas.repository;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {
    List<LogAuditoria> findAllByOrderByDataHoraDesc();
<<<<<<< HEAD

    // Versão paginada para os logs
    Page<LogAuditoria> findAllByOrderByDataHoraDesc(Pageable pageable);

=======
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
    List<LogAuditoria> findByUsuarioCpfOrderByDataHoraDesc(String usuarioCpf);
    List<LogAuditoria> findByEntidadeIdOrderByDataHoraDesc(UUID entidadeId);
}