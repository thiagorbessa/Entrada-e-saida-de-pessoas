package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pacientes_internados", indexes = {
        @Index(name = "idx_paciente_int_cpf", columnList = "cpf"),
        @Index(name = "idx_paciente_int_nome", columnList = "nome"),
        @Index(name = "idx_paciente_int_unidade", columnList = "unidade")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PacienteInternado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(length = 11, nullable = false, unique = true)
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

    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusInternacao status = StatusInternacao.INTERNADO;

    @Column(nullable = false)
    private LocalDateTime horaEntrada;

    private LocalDateTime horaSaida;

    @ManyToOne
    @JoinColumn(name = "usuario_entrada_id")
    private Usuario usuarioEntrada;

    @ManyToOne
    @JoinColumn(name = "usuario_saida_id")
    private Usuario usuarioSaida;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("horaEntrada DESC")
    private List<Acompanhante> acompanhantes = new ArrayList<>();

    @Transient
    public StatusInternacao getStatusEfetivo() {
        if (this.status != null) {
            return this.status;
        }
        return this.horaSaida == null ? StatusInternacao.INTERNADO : StatusInternacao.ALTA;
    }

    @Transient
    public boolean isInternadoAtivo() {
        return getStatusEfetivo() == StatusInternacao.INTERNADO && horaSaida == null;
    }

    @Transient
    public boolean isObito() {
        return getStatusEfetivo() == StatusInternacao.OBITO;
    }

    @Transient
    public boolean isNovo() {
        return id == null;
    }

    @Column(name = "limite_diario_acompanhantes")
    private Integer limiteDiarioAcompanhantes = 5;

    @Column(name = "autorizado_por", length = 200)
    private String autorizadoPor;

    public int getLimiteDiarioEfetivo() {
        return limiteDiarioAcompanhantes != null ? limiteDiarioAcompanhantes : 5;
    }

    public boolean isLimiteDiarioIlimitado() {
        return getLimiteDiarioEfetivo() == 0;
    }
}