package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "registros", indexes = {
        @Index(name = "idx_registro_pessoa_id", columnList = "pessoa_id"),
        @Index(name = "idx_registro_hora_entrada", columnList = "horaEntrada"),
        @Index(name = "idx_registro_hora_saida", columnList = "horaSaida")
})
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

    @Column(name = "usuario_entrada_nome")
    private String usuarioEntradaNome;

    @Column(name = "usuario_entrada_cpf")
    private String usuarioEntradaCpf;

    @Column(name = "usuario_saida_nome")
    private String usuarioSaidaNome;

    @Column(name = "usuario_saida_cpf")
    private String usuarioSaidaCpf;

    // ==========================================
    // MÉTODOS DE CONVENIÊNCIA PARA O HISTÓRICO
    // ==========================================

    public String getOperadorEntradaNome() {
        if (usuarioEntrada != null && usuarioEntrada.getNomeCompleto() != null) {
            return usuarioEntrada.getNomeCompleto();
        }
        if (usuarioEntradaNome != null && !usuarioEntradaNome.isBlank()) {
            return usuarioEntradaCpf != null && !usuarioEntradaCpf.isBlank()
                    ? usuarioEntradaNome + " (CPF: " + usuarioEntradaCpf + ")"
                    : usuarioEntradaNome;
        }
        return "-";
    }

    public String getOperadorSaidaNome() {
        if (usuarioSaida != null && usuarioSaida.getNomeCompleto() != null) {
            return usuarioSaida.getNomeCompleto();
        }
        if (usuarioSaidaNome != null && !usuarioSaidaNome.isBlank()) {
            return usuarioSaidaCpf != null && !usuarioSaidaCpf.isBlank()
                    ? usuarioSaidaNome + " (CPF: " + usuarioSaidaCpf + ")"
                    : usuarioSaidaNome;
        }
        return "-";
    }
}