package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.exception.RecursoNaoEncontradoException;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/entrada")
    public String registrarEntrada(@RequestParam("pessoaId") UUID pessoaId,
                                   Authentication auth,
                                   RedirectAttributes redirectAttributes) {
        Usuario operador = usuarioRepository.findByCpf(auth.getName())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Operador autenticado não encontrado."));

        registroService.registrarEntrada(pessoaId, operador);
        redirectAttributes.addFlashAttribute("sucesso", "Entrada registrada com sucesso!");
        return "redirect:/pessoas";
    }

    @PostMapping("/saida")
    public String registrarSaida(@RequestParam("registroId") UUID registroId,
                                 @RequestParam(value = "pessoaId", required = false) UUID pessoaId,
                                 Authentication auth,
                                 RedirectAttributes redirectAttributes) {
        Usuario operador = usuarioRepository.findByCpf(auth.getName())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Operador autenticado não encontrado."));

        registroService.registrarSaida(registroId, operador);
        redirectAttributes.addFlashAttribute("sucesso", "Saída registrada com sucesso!");
        return "redirect:/pessoas";
    }
}