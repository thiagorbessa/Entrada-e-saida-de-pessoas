package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final UsuarioRepository usuarioRepository;

    private Usuario obterUsuarioLogado(Principal principal) {
        if (principal == null) return null;
        return usuarioRepository.findByCpf(principal.getName()).orElse(null);
    }

    @GetMapping("/logs")
    public String verLogs(Model model, Principal principal) {
        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas"; // Acesso restrito apenas ao Administrador Geral
        }

        List<LogAuditoria> logs = auditoriaService.listarTodos();
        model.addAttribute("logs", logs);
        model.addAttribute("usuarioLogado", usuario);
        return "auditoria/logs";
    }
}