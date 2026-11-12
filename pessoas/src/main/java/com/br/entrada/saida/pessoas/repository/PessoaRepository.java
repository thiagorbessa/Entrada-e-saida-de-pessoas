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

    List<Pessoa> findByUnidadeIn(Set<Unidade> unidades);

    Page<Pessoa> findByUnidadeIn(Set<Unidade> unidades, Pageable pageable);

    // ==========================================================
    // MÉTODOS PAGINADOS COM SUPORTE À ORDENAÇÃO POR HORA DE ENTRADA E SAÍDA
    // ==========================================================
    @Query(value = """
        SELECT p FROM Pessoa p
        LEFT JOIN Registro r ON r.pessoa.id = p.id AND r.horaEntrada = (
            SELECT MAX(r2.horaEntrada) FROM Registro r2 WHERE r2.pessoa.id = p.id
        )
        WHERE (:termo IS NULL OR :termo = ''
           OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%')))
    """,
            countQuery = """
        SELECT COUNT(p) FROM Pessoa p
        WHERE (:termo IS NULL OR :termo = ''
           OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%')))
    """)
    Page<Pessoa> buscarPorTermoPaginado(@Param("termo") String termo, Pageable pageable);

    @Query(value = """
        SELECT p FROM Pessoa p
        LEFT JOIN Registro r ON r.pessoa.id = p.id AND r.horaEntrada = (
            SELECT MAX(r2.horaEntrada) FROM Registro r2 WHERE r2.pessoa.id = p.id
        )
        WHERE p.unidade IN :unidades
          AND (:termo IS NULL OR :termo = ''
            OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%')))
    """,
            countQuery = """
        SELECT COUNT(p) FROM Pessoa p
        WHERE p.unidade IN :unidades
          AND (:termo IS NULL OR :termo = ''
            OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.identidade) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(p.telefone) LIKE LOWER(CONCAT('%', :termo, '%')))
    """)
    Page<Pessoa> buscarPorTermoEUnidadesPaginado(@Param("termo") String termo, @Param("unidades") Set<Unidade> unidades, Pageable pageable);

    Optional<Pessoa> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<Pessoa> findByIdentidade(String identidade);
    Optional<Pessoa> findByNomeIgnoreCase(String nome);
}