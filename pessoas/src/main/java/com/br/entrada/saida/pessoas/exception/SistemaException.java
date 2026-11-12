package com.br.entrada.saida.pessoas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class SistemaException extends RuntimeException {

    private final HttpStatus status;
    private final String titulo;

    protected SistemaException(String titulo, String message, HttpStatus status) {
        super(message);
        this.titulo = titulo;
        this.status = status;
    }
}