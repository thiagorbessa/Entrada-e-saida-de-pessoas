package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "acompanhantes", indexes = {
        @Index(name = "idx_acomp_cpf", columnList = "cpf"),
        @Index(name = "idx_acomp_paciente_id", columnList = "paciente_id"),
        @Index(name = "idx_acomp_hora_entrada", columnList = "horaEntrada")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Acompanhante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(length = 11, nullable = false)
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;

    private String telefone;

    @NotNull(message = "A unidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unidade unidade;

    @NotBlank(message = "O andar é obrigatório")
    @Column(nullable = false)
    private String andar;

    @NotBlank(message = "O leito é obrigatório")
    @Column(nullable = false)
    private String leito;

    // Vínculo obrigatório com o paciente internado
    @NotNull(message = "O paciente vinculado é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteInternado paciente;

    @Column(nullable = false)
    private LocalDateTime horaEntrada;

    private LocalDateTime horaSaida;

    @ManyToOne
    @JoinColumn(name = "usuario_entrada_id")
    private Usuario usuarioEntrada;

    @ManyToOne
    @JoinColumn(name = "usuario_saida_id")
    private Usuario usuarioSaida;

    @Transient
    public boolean isAtivo() {
        return horaEntrada != null && horaSaida == null;
    }
}