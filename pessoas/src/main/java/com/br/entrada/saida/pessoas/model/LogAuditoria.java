package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index; // <--- O IMPORT CORRETO DO JPA

@Entity
@Table(name = "log_auditoria", indexes = {
        @Index(name = "idx_log_data_hora", columnList = "dataHora"),
        @Index(name = "idx_log_usuario_cpf", columnList = "usuarioCpf"),
        @Index(name = "idx_log_entidade_id", columnList = "entidadeId")
})
@Getter
@Setter
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(nullable = false)
    private String usuarioCpf;

    @Column(nullable = false)
    private String usuarioNome;

    @Column(nullable = false)
    private String acao; // 'CRIACAO', 'EDICAO', 'EXCLUSAO'

    private String entidade; // 'PESSOA' ou 'USUARIO'

    private UUID entidadeId;

    private String entidadeDescricao; // Nome ou CPF da pessoa/usuário afetado

    @Column(columnDefinition = "TEXT")
    private String detalhes; // O que mudou (ex: "Setor alterado de X para Y")
}