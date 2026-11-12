package com.br.entrada.saida.pessoas.exception;

import org.springframework.http.HttpStatus;

public class RegraNegocioException extends SistemaException {

    public RegraNegocioException(String message) {
        super("Operação não permitida", message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public RegraNegocioException(String titulo, String message) {
        super(titulo, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}