package com.br.entrada.saida.pessoas.dto;

import com.br.entrada.saida.pessoas.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaRequestDTO {

    private String id;

    private String cpf;
    private String identidade;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String telefone;

    @NotNull(message = "A unidade é obrigatória")
    private Unidade unidade;

    @NotBlank(message = "O setor é obrigatório")
    private String setor;

    @NotBlank(message = "O andar é obrigatório")
    private String andar;

    @NotBlank(message = "O paciente é obrigatório")
    private String paciente;

    private String observacao;
}