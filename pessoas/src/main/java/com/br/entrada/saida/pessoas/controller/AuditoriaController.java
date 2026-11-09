package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/backup")
    public String consultarBackup(@RequestParam(value = "ano", required = false) Integer ano, Model model, Principal principal) {
        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas"; // Acesso restrito apenas ao Admin Geral
        }

        if (ano != null) {
            try {
                // Tenta buscar os dados da tabela dinâmica do ano correspondente no MySQL
                List<LogAuditoria> logsBackup = auditoriaService.listarLogsPorAnoBackup(ano);
                model.addAttribute("logs", logsBackup);
                model.addAttribute("anoPesquisado", ano);

                if (logsBackup.isEmpty()) {
                    model.addAttribute("erro", "A tabela de backup para o ano " + ano + " está vazia.");
                }
            } catch (Exception e) {
                // Se a tabela não existir no banco do Docker, o MySQL dispara exceção tratada aqui
                model.addAttribute("erro", "Não existe backup registrado ou tabela encontrada para o ano de " + ano + ".");
            }
        }

        model.addAttribute("usuarioLogado", usuario);
        return "auditoria/backup";
    }

    // 1. Exibe a listagem de pessoas do ano de backup digitado
    @GetMapping("/backup/pessoas")
    public String listarPessoasBackup(@RequestParam("ano") int ano, Model model, Principal principal) {
        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas";
        }

        try {
            List<Pessoa> pessoas = auditoriaService.listarPessoasPorAnoBackup(ano);
            model.addAttribute("pessoas", pessoas);
            model.addAttribute("ano", ano);
        } catch (Exception e) {
            model.addAttribute("erro", "Não existe tabela de backup de pessoas para o ano de " + ano + ".");
        }

        model.addAttribute("usuarioLogado", usuario);
        return "auditoria/listar-backup";
    }

    // 2. Exibe o histórico de visitas da pessoa dentro daquele ano de backup
    @GetMapping("/backup/historico")
    public String historicoPessoaBackup(@RequestParam("id") UUID pessoaId, @RequestParam("ano") int ano, Model model, Principal principal) {
        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas";
        }

        try {
            Pessoa pessoa = auditoriaService.buscarPessoaPorIdNoBackup(pessoaId, ano);
            List<Registro> registros = auditoriaService.listarRegistrosPorPessoaEAnodeBackup(pessoaId, ano);

            model.addAttribute("pessoa", pessoa);
            model.addAttribute("registros", registros);
            model.addAttribute("ano", ano);
        } catch (Exception e) {
            System.err.println("EXCECAO REAL NO HISTORICO: " + e.getMessage());
            e.printStackTrace(); // Mostra o erro real no console do Docker
            model.addAttribute("erro", "Erro ao carregar dados do backup: " + e.getMessage());
        }

        model.addAttribute("usuarioLogado", usuario);
        return "auditoria/historico-backup";
    }

    @GetMapping("/backup/logs-ano")
    public String listarLogsAnoBackup(@RequestParam("ano") int ano, Model model, Principal principal) {
        Usuario usuario = obterUsuarioLogado(principal);
        if (usuario == null || !"ROLE_GERAL".equals(usuario.getPerfil())) {
            return "redirect:/pessoas";
        }

        try {
            List<LogAuditoria> logsBackup = auditoriaService.listarLogsPorAnoBackup(ano);
            model.addAttribute("logs", logsBackup);
            model.addAttribute("ano", ano);
        } catch (Exception e) {
            model.addAttribute("erro", "Não existe tabela de backup de logs para o ano de " + ano + ".");
        }

        model.addAttribute("usuarioLogado", usuario);
        return "auditoria/logs-backup";
    }
}