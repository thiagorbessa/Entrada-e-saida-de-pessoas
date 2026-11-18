package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String perfil;

    @Column(name = "secret_2fa")
    private String secret2fa;

    @Column(name = "mfa_ativo", nullable = false)
    private boolean mfaAtivo = false;

    @Column(name = "acessos_sem_mfa")
    private Integer acessosSemMfa = 0;

    @Column(name = "limite_acessos_sem_mfa")
    private Integer limiteAcessosSemMfa = 3;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_unidades", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade")
    private Set<Unidade> unidades = new HashSet<>();
}