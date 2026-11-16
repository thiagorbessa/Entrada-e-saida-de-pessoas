package com.br.entrada.saida.pessoas.dto;

import com.br.entrada.saida.pessoas.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record AcompanhanteRecord(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O CPF é obrigatório") @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos") String cpf,
        String telefone,
        @NotBlank(message = "O leito é obrigatório") String leito,
        @NotNull(message = "A unidade é obrigatória") Unidade unidade,
        @NotBlank(message = "O andar é obrigatório") String andar,
        @NotNull(message = "É obrigatório selecionar o paciente internado") UUID pacienteId
) {}