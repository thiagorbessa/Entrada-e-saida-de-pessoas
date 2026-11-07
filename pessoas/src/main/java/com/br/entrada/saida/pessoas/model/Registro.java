package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    @Enumerated(EnumType.STRING)
    private Unidade unidade; // Alterado para o Enum

    private String setor;
    private String andar;
    private String paciente;

    @ManyToOne
    @JoinColumn(name = "usuario_entrada_id")
    private Usuario usuarioEntrada;

    @ManyToOne
    @JoinColumn(name = "usuario_saida_id")
    private Usuario usuarioSaida;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}