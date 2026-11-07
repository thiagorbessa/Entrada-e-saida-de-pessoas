package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String perfil; // Ex: ROLE_GERAL, ROLE_SISTEMA, ROLE_USER

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_unidades", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade")
    private Set<Unidade> unidades = new HashSet<>();
}