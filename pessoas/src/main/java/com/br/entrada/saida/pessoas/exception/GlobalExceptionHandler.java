package com.br.entrada.saida.pessoas.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private String redirecionarComErro(String mensagem, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("erro", mensagem);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/pessoas");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return redirecionarComErro(ex.getMessage(), redirectAttributes, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return redirecionarComErro(ex.getMessage(), redirectAttributes, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String mensagemErro = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return redirecionarComErro(mensagemErro, redirectAttributes, request);
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return redirecionarComErro("Ocorreu um erro no sistema: " + ex.getMessage(), redirectAttributes, request);
    }
}