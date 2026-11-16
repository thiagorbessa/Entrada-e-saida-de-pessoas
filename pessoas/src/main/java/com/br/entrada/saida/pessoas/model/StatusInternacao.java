package com.br.entrada.saida.pessoas.model;

public enum StatusInternacao {
    INTERNADO("Internado"),
    ALTA("Alta"),
    OBITO("Óbito");

    private final String descricao;

    StatusInternacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}