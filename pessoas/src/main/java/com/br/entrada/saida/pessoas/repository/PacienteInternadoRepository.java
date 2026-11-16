package com.br.entrada.saida.pessoas.repository;

import com.br.entrada.saida.pessoas.model.PacienteInternado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteInternadoRepository extends JpaRepository<PacienteInternado, UUID> {
    Optional<PacienteInternado> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndHoraSaidaIsNull(String cpf);
    List<PacienteInternado> findByHoraSaidaIsNullOrderByNomeAsc();
    List<PacienteInternado> findAllByOrderByHoraEntradaDesc();
}