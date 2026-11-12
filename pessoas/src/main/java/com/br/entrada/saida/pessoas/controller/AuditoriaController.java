package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.AuditoriaService;
import com.br.entrada.saida.pessoas.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final BackupService backupService;
    private final UsuarioRepository usuarioRepository;

    private Usuario obterUsuarioLogado(Principal principal) {
        if (principal == null) return null;
        return usuarioRepository.findByCpf(principal.getName()).orElse(null);
    }

    private boolean ehAdminGeral(Usuario usuario) {
        return usuario != null && "ROLE_GERAL".equals(usuario.getPerfil());
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
            return "redirect:/pessoas";
        }

        if (ano != null) {
            boolean existe = backupService.verificarExistenciaBackup(ano);
            if (!existe) {
                model.addAttribute("erro", "Nenhum backup registrado ou tabela encontrada para o ano de " + ano + ".");
            } else {
                model.addAttribute("anoPesquisado", ano);
            }
        }

        model.addAttribute("anoAtual", Year.now().getValue());
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

    // ==========================================================
    // NOVO: arquivar o ano (backup e manter / backup e apagar)
    // ==========================================================

    @PostMapping("/backup/arquivar")
    public String arquivar(@RequestParam("ano") int ano,
                           @RequestParam("modo") String modo,
                           @RequestParam(value = "confirmacao", required = false) String confirmacao,
                           Principal principal,
                           RedirectAttributes redirect) {

        Usuario usuario = obterUsuarioLogado(principal);
        if (!ehAdminGeral(usuario)) {
            return "redirect:/pessoas";
        }

        boolean apagar = "apagar".equals(modo);
        if (!apagar && !"manter".equals(modo)) {
            redirect.addFlashAttribute("erro", "Operação inválida.");
            return "redirect:/auditoria/backup";
        }

        if (apagar && !("APAGAR " + ano).equals(confirmacao == null ? "" : confirmacao.trim())) {
            redirect.addFlashAttribute("erro", "Confirmação incorreta. Nada foi alterado. Digite exatamente: APAGAR " + ano);
            return "redirect:/auditoria/backup";
        }

        try {
            BackupService.ResultadoArquivamento r = backupService.arquivarAno(ano, apagar);

            String mensagem = "Backup do ano " + ano + " concluído: "
                    + r.registrosCopiados() + " registros novos copiados para registros_" + ano + ", "
                    + r.pessoasNoBackup() + " pessoas atualizadas em pessoas_" + ano + " e "
                    + r.logsCopiados() + " logs gravados em logs_" + ano + ".";
            if (apagar) {
                mensagem += " Removidos do sistema: " + r.registrosApagados() + " registros e "
                        + r.pessoasApagadas() + " pessoas.";
            }

            try {
                auditoriaService.registrar(usuario,
                        apagar ? "BACKUP_E_APAGAR" : "BACKUP_E_MANTER",
                        "BACKUP", null, "Arquivamento do ano " + ano, mensagem);
            } catch (Exception e) {
                System.err.println("Falha ao registrar log do arquivamento: " + e.getMessage());
            }

            redirect.addFlashAttribute("sucesso", mensagem);
        } catch (com.br.entrada.saida.pessoas.exception.SistemaException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirect.addFlashAttribute("erro", "Falha inesperada ao arquivar o ano " + ano + " (nada foi apagado).");
        }

        return "redirect:/auditoria/backup";
    }

    @PostMapping("/backup/importar")
    public String importar(@RequestParam("arquivo") MultipartFile arquivo,
                           @RequestParam(value = "confirmacao", required = false) String confirmacao,
                           Principal principal,
                           RedirectAttributes redirect) {

        Usuario usuario = obterUsuarioLogado(principal);
        if (!ehAdminGeral(usuario)) {
            return "redirect:/pessoas";
        }

        if (!"IMPORTAR".equals(confirmacao == null ? "" : confirmacao.trim())) {
            redirect.addFlashAttribute("erro", "Confirmação incorreta. Nada foi alterado. Digite exatamente: IMPORTAR");
            return "redirect:/auditoria/backup";
        }

        try {
            backupService.importarSql(arquivo);
            String mensagem = "Importação concluída a partir de '" + arquivo.getOriginalFilename() + "'.";

            try {
                auditoriaService.registrar(usuario, "IMPORTAR_BACKUP", "BACKUP", null,
                        "Importação de dump SQL", mensagem);
            } catch (Exception e) {
                System.err.println("Falha ao registrar log da importação: " + e.getMessage());
            }

            redirect.addFlashAttribute("sucesso", mensagem);
        } catch (com.br.entrada.saida.pessoas.exception.SistemaException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirect.addFlashAttribute("erro",
                    "Falha inesperada na importação. O banco pode estar parcialmente restaurado; refaça a importação com um arquivo válido.");
        }

        return "redirect:/auditoria/backup";
    }
}