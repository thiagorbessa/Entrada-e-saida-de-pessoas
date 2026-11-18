package com.br.entrada.saida.pessoas.configuration;

import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MfaInterceptor implements HandlerInterceptor {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final Usuario usuario;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (usuario != null && !usuario.isMfaAtivo()) {
            int acessos = usuario.getAcessosSemMfa() == null ? 0 : usuario.getAcessosSemMfa();
            int limite = usuario.getLimiteAcessosSemMfa() == null ? 3 : usuario.getLimiteAcessosSemMfa();

            if (acessos > limite) {
                String uri = request.getRequestURI();

                if (!uri.startsWith("/2fa/") && !uri.startsWith("/logout")
                        && !uri.startsWith("/css/") && !uri.startsWith("/js/")
                        && !uri.startsWith("/images/") && !uri.startsWith("/img/")) {

                    response.sendRedirect(request.getContextPath() + "/2fa/configurar?bloqueado=true");
                    return false;
                }
            }
        }
        return true;
    }
}