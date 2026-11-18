package com.br.entrada.saida.pessoas.configuration;

import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String cpf = event.getAuthentication().getName();

        usuarioRepository.findByCpf(cpf).ifPresent(usuario -> {
            if (!usuario.isMfaAtivo()) {
                usuario.setAcessosSemMfa(usuario.getAcessosSemMfa() == null ? 1 : usuario.getAcessosSemMfa() + 1);
                usuarioRepository.save(usuario);
            }
        });
    }
}