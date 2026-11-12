package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.exception.AcessoNegadoException;
import com.br.entrada.saida.pessoas.exception.RecursoNaoEncontradoException;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.AuditoriaService;
import com.br.entrada.saida.pessoas.service.MfaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MfaController {

    private static final String SESSION_TEMP_SECRET = "MFA_TEMP_SECRET";

    private final UsuarioRepository usuarioRepository;
    private final MfaService mfaService;
    private final AuditoriaService auditoriaService;

    private Usuario getUsuarioLogado(Authentication auth) {
        if (auth == null || auth.getName() == null) return null;
        return usuarioRepository.findByCpf(auth.getName()).orElse(null);
    }

    // ==================== 1. ETAPA DE LOGIN (/login-2fa) ====================

    @GetMapping("/login-2fa")
    public String paginaLogin2fa() {
        return "login-2fa";
    }

    @PostMapping("/login-2fa")
    public String verificarLogin2fa(@RequestParam("codigo") String codigo,
                                    Authentication auth,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        Usuario usuario = getUsuarioLogado(auth);
        if (usuario == null || !usuario.isMfaAtivo()) {
            return "redirect:/";
        }

        if (!mfaService.validarCodigo(usuario.getSecret2fa(), codigo)) {
            redirectAttributes.addFlashAttribute("erro", "Código inválido ou expirado. Tente novamente.");
            return "redirect:/login-2fa";
        }

        // Promove a sessão de ROLE_PRE_AUTH_2FA para o perfil real do usuário (ex: ROLE_GERAL)
        Authentication authCompleta = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(),
                auth.getCredentials(),
                List.of(new SimpleGrantedAuthority(usuario.getPerfil()))
        );
        SecurityContextHolder.getContext().setAuthentication(authCompleta);
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return "redirect:/pessoas";
    }

    // ==================== 2. CONFIGURAÇÃO DO PRÓPRIO USUÁRIO ====================

    @GetMapping("/2fa/configurar")
    public String configurar2fa(Model model, Authentication auth, HttpSession session) {
        Usuario usuario = getUsuarioLogado(auth);
        if (usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogado", usuario);
        model.addAttribute("mfaAtivo", usuario.isMfaAtivo());

        if (!usuario.isMfaAtivo()) {
            String tempSecret = (String) session.getAttribute(SESSION_TEMP_SECRET);
            if (tempSecret == null) {
                tempSecret = mfaService.gerarNovaChaveSecreta();
                session.setAttribute(SESSION_TEMP_SECRET, tempSecret);
            }
            String qrCodeUri = mfaService.gerarQrCodeDataUri(tempSecret, usuario.getCpf());
            model.addAttribute("qrCodeUri", qrCodeUri);
            model.addAttribute("secretManual", tempSecret);
        }

        return "usuarios/configurar-2fa";
    }

    @PostMapping("/2fa/ativar")
    public String ativar2fa(@RequestParam("codigo") String codigo,
                            Authentication auth,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Usuario usuario = getUsuarioLogado(auth);
        String tempSecret = (String) session.getAttribute(SESSION_TEMP_SECRET);

        if (usuario == null || tempSecret == null) {
            return "redirect:/2fa/configurar";
        }

        if (!mfaService.validarCodigo(tempSecret, codigo)) {
            redirectAttributes.addFlashAttribute("erro",
                    "Código incorreto. Escaneie o QR Code novamente e digite os 6 dígitos exibidos no aplicativo.");
            return "redirect:/2fa/configurar";
        }

        usuario.setSecret2fa(tempSecret);
        usuario.setMfaAtivo(true);
        usuarioRepository.save(usuario);
        session.removeAttribute(SESSION_TEMP_SECRET);

        UUID uuidLog = UUID.nameUUIDFromBytes(usuario.getId().toString().getBytes());
        auditoriaService.registrar(usuario, "ATIVACAO_2FA", "USUARIO", uuidLog,
                usuario.getNomeCompleto(), "Ativou a autenticação em duas etapas (Google Authenticator)");

        redirectAttributes.addFlashAttribute("sucesso", "Verificação em duas etapas ativada com sucesso!");
        return "redirect:/2fa/configurar";
    }

    @PostMapping("/2fa/desativar")
    public String desativar2fa(@RequestParam("codigo") String codigo,
                               Authentication auth,
                               RedirectAttributes redirectAttributes) {
        Usuario usuario = getUsuarioLogado(auth);
        if (usuario == null || !usuario.isMfaAtivo()) {
            return "redirect:/2fa/configurar";
        }

        if (!mfaService.validarCodigo(usuario.getSecret2fa(), codigo)) {
            redirectAttributes.addFlashAttribute("erro",
                    "Código inválido. Informe o código atual do aplicativo para desativar o 2FA.");
            return "redirect:/2fa/configurar";
        }

        usuario.setSecret2fa(null);
        usuario.setMfaAtivo(false);
        usuarioRepository.save(usuario);

        UUID uuidLog = UUID.nameUUIDFromBytes(usuario.getId().toString().getBytes());
        auditoriaService.registrar(usuario, "DESATIVACAO_2FA", "USUARIO", uuidLog,
                usuario.getNomeCompleto(), "Desativou a autenticação em duas etapas");

        redirectAttributes.addFlashAttribute("sucesso", "Verificação em duas etapas desativada.");
        return "redirect:/2fa/configurar";
    }

    // ==================== 3. RESET PELO ADMIN GERAL ====================

    @PostMapping("/2fa/resetar/{id}")
    public String resetar2faAdmin(@PathVariable Long id,
                                  Authentication auth,
                                  RedirectAttributes redirectAttributes) {
        Usuario admin = getUsuarioLogado(auth);
        if (admin == null || !"ROLE_GERAL".equals(admin.getPerfil())) {
            throw new AcessoNegadoException("Apenas o Administrador Geral pode resetar o 2FA de outros usuários.");
        }

        Usuario alvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        alvo.setSecret2fa(null);
        alvo.setMfaAtivo(false);
        usuarioRepository.save(alvo);

        UUID uuidLog = UUID.nameUUIDFromBytes(alvo.getId().toString().getBytes());
        auditoriaService.registrar(admin, "RESET_2FA", "USUARIO", uuidLog,
                alvo.getNomeCompleto(), "Resetou a autenticação em duas etapas do usuário");

        redirectAttributes.addFlashAttribute("sucesso",
                "Autenticação em duas etapas resetada para " + alvo.getNomeCompleto() + ".");
        return "redirect:/usuarios/" + id + "/editar";
    }
}