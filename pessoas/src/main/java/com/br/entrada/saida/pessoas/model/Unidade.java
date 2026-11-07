package com.br.entrada.saida.pessoas.model;

public enum Unidade {
    HC1("HC1"),
    HC2("HC2"),
    HC3("HC3"),
    HC4("HC4"),
    COAD("COAD"),
    MP("MP"),
    CPQ("CPQ"),
    DIPAT("DIPAT");

    private final String descricao;

    Unidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}