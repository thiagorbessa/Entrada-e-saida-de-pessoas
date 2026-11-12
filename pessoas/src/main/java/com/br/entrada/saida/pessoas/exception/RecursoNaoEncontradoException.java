package com.br.entrada.saida.pessoas.exception;

import org.springframework.http.HttpStatus;

public class RecursoNaoEncontradoException extends SistemaException {

    public RecursoNaoEncontradoException(String message) {
        super("Registro não encontrado", message, HttpStatus.NOT_FOUND);
    }
}