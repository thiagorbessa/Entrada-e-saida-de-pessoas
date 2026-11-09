package com.br.entrada.saida.pessoas.repository;

import com.br.entrada.saida.pessoas.model.Registro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID> {

    boolean existsByPessoaIdAndHoraSaidaIsNull(UUID pessoaId);

    Optional<Registro> findByPessoaIdAndHoraSaidaIsNull(UUID pessoaId);

    List<Registro> findByHoraSaidaIsNull();

    //  Método paginado para buscar o histórico de registros da pessoa ordenado do mais recente para o mais antigo
    Page<Registro> findByPessoaIdOrderByHoraEntradaDesc(UUID pessoaId, Pageable pageable);

}