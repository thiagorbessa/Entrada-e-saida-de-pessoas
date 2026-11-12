package com.br.entrada.saida.pessoas.exception;

import org.springframework.http.HttpStatus;

public class AcessoNegadoException extends SistemaException {

    public AcessoNegadoException(String message) {
        super("Acesso negado", message, HttpStatus.FORBIDDEN);
    }
}