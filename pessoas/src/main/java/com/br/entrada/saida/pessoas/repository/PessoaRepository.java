package com.br.entrada.saida.pessoas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Unidade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("""
        SELECT p FROM Pessoa p
        WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    List<Pessoa> buscarPorTermo(@Param("termo") String termo);

    @Query("""
        SELECT p FROM Pessoa p
        WHERE p.unidade IN :unidades
          AND (
               LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%'))
          )
    """)
    List<Pessoa> buscarPorTermoEUnidades(@Param("termo") String termo, @Param("unidades") Set<Unidade> unidades);

    // Método original (mantido)
    List<Pessoa> findByUnidadeIn(Set<Unidade> unidades);

    // Versão paginada exigida pelo PessoaService
    Page<Pessoa> findByUnidadeIn(Set<Unidade> unidades, Pageable pageable);

    // ==========================================================
    // MÉTODOS PAGINADOS PARA A BARRA DE BUSCA
    // ==========================================================
    @Query("""
        SELECT p FROM Pessoa p
        WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    Page<Pessoa> buscarPorTermoPaginado(@Param("termo") String termo, Pageable pageable);

    @Query("""
        SELECT p FROM Pessoa p
        WHERE p.unidade IN :unidades
          AND (
               LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%'))
          )
    """)
    Page<Pessoa> buscarPorTermoEUnidadesPaginado(@Param("termo") String termo, @Param("unidades") Set<Unidade> unidades, Pageable pageable);

    Optional<Pessoa> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<Pessoa> findByIdentidade(String identidade);
    Optional<Pessoa> findByNomeIgnoreCase(String nome);
}