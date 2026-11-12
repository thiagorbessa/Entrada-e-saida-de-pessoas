package com.br.entrada.saida.pessoas.exception;

public class CpfDuplicadoException extends RegraNegocioException {
    public CpfDuplicadoException(String message) {
        super("CPF duplicado", message);
    }
}