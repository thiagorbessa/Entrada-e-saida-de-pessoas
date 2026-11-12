package com.br.entrada.saida.pessoas.configuration;

import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TwoFactorAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final String ROLE_PRE_AUTH_2FA = "ROLE_PRE_AUTH_2FA";

    private final UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Usuario usuario = usuarioRepository.findByCpf(authentication.getName()).orElse(null);

        if (usuario != null && usuario.isMfaAtivo()) {
            // Substitui temporariamente as permissões pela role restrita de verificação 2FA
            Authentication preAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    List.of(new SimpleGrantedAuthority(ROLE_PRE_AUTH_2FA))
            );
            SecurityContextHolder.getContext().setAuthentication(preAuth);
            request.getSession().setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

            response.sendRedirect(request.getContextPath() + "/login-2fa");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/pessoas");
    }
}