package com.br.entrada.saida.pessoas.dto;

import com.br.entrada.saida.pessoas.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioRequestDTO {

    private Long id;

    @NotBlank(message = "O nome completo é obrigatório")
    private String nomeCompleto;

    @NotBlank(message = "O CPF (Login) é obrigatório")
    private String cpf;

    private String senha;

    @NotBlank(message = "O perfil de acesso é obrigatório")
    private String perfil;

    // Novo campo para receber as unidades selecionadas no formulário
    private Set<Unidade> unidades = new HashSet<>();
}