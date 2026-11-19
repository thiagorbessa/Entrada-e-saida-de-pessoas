package com.br.entrada.saida.pessoas.repository;

import com.br.entrada.saida.pessoas.model.Acompanhante;
import com.br.entrada.saida.pessoas.model.PacienteInternado;
import com.br.entrada.saida.pessoas.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AcompanhanteRepository extends JpaRepository<Acompanhante, UUID> {
    long countByPacienteAndHoraSaidaIsNull(PacienteInternado paciente);
    long countByPacienteAndHoraEntradaBetween(PacienteInternado paciente, LocalDateTime inicioDia, LocalDateTime fimDia);
    long countByPaciente(PacienteInternado paciente);
    boolean existsByCpfAndHoraSaidaIsNull(String cpf);
    Optional<Acompanhante> findFirstByCpfAndPacienteIdOrderByHoraEntradaDesc(String cpf, UUID pacienteId);
    List<Acompanhante> findAllByOrderByHoraEntradaDesc();

    List<Acompanhante> findByUnidadeInOrderByHoraEntradaDesc(Set<Unidade> unidades);
}