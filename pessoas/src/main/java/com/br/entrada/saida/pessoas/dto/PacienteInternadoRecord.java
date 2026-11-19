package com.br.entrada.saida.pessoas.dto;

import com.br.entrada.saida.pessoas.model.Unidade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacienteInternadoRecord(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "CPF é obrigatório") String cpf,
        String telefone,
        @NotNull(message = "Unidade é obrigatória") Unidade unidade,
        @NotBlank(message = "Andar é obrigatório") String andar,
        @NotBlank(message = "Leito é obrigatório") String leito,
        String observacao,
        @Min(value = 0, message = "O limite de acompanhantes simultâneos deve ser 0 (ilimitado) ou maior") Integer limiteSimultaneosAcompanhantes,
        @Min(value = 0, message = "O limite total de acompanhantes deve ser 0 (ilimitado) ou maior") Integer limiteTotalAcompanhantes,
        String autorizadoPor,
        @Min(value = 0, message = "A capacidade diária deve ser 0 (ilimitado) ou maior") Integer limiteDiarioAcompanhantes
) {}