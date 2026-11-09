package com.br.entrada.saida.pessoas.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "formulario"; // Ou redirecionar para a página adequada com o erro
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "formulario";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        String mensagemErro = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        model.addAttribute("erro", mensagemErro);
        return "formulario";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("erro", "Ocorreu um erro inesperado no sistema. Tente novamente.");
        return "formulario";
    }
}