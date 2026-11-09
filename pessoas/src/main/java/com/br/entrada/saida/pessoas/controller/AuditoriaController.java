package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
    public String verLogs(
            @PageableDefault(page = 0, size = 100, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable,
            Model model, Principal principal) {

        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas"; // Acesso restrito apenas ao Administrador Geral
        }

        Page<LogAuditoria> paginaLogs = auditoriaService.listarTodosPaginados(pageable);

        model.addAttribute("logs", paginaLogs.getContent());
        model.addAttribute("paginaLogs", paginaLogs);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", paginaLogs.getTotalPages());
        model.addAttribute("totalItems", paginaLogs.getTotalElements());
        model.addAttribute("usuarioLogado", usuario);

        return "auditoria/logs";
    }
}