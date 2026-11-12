package com.br.entrada.saida.pessoas.exception;

public class CpfPessoaDuplicadoException extends RegraNegocioException {
    public CpfPessoaDuplicadoException(String message) {
        super("CPF duplicado", message);
    }
}