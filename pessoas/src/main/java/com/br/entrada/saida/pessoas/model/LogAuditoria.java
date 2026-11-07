package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_auditoria")
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