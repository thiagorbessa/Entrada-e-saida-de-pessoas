package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/entrada")
    public String registrarEntrada(@RequestParam("pessoaId") UUID pessoaId, Authentication auth) {
        // Captura o operador que está realizando a entrada
        Usuario operador = usuarioRepository.findByCpf(auth.getName())
                .orElseThrow(() -> new RuntimeException("Operador não encontrado"));

        registroService.registrarEntrada(pessoaId, operador);
        return "redirect:/pessoas";
    }

    @PostMapping("/saida")
    public String registrarSaida(@RequestParam("registroId") UUID registroId, Authentication auth) {
        // Captura o operador que está realizando a saída
        Usuario operador = usuarioRepository.findByCpf(auth.getName())
                .orElseThrow(() -> new RuntimeException("Operador não encontrado"));

        registroService.registrarSaida(registroId, operador);
        return "redirect:/pessoas";
    }
}