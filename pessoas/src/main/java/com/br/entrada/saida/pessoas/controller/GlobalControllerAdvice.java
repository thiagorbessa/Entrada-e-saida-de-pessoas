package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final UsuarioRepository usuarioRepository;

    @ModelAttribute
    public void adicionarAvisoMfa(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            usuarioRepository.findByCpf(auth.getName()).ifPresent(usuario -> {
                if (!usuario.isMfaAtivo()) {
                    int acessos = usuario.getAcessosSemMfa() == null ? 0 : usuario.getAcessosSemMfa();
                    int limite = usuario.getLimiteAcessosSemMfa() == null ? 3 : usuario.getLimiteAcessosSemMfa();

                    if (acessos <= limite) {
                        int restantes = limite - acessos + 1;
                        String mensagem = restantes == 1
                                ? "ÚLTIMO AVISO: No seu próximo login, a Autenticação de 2 Fatores será obrigatória!"
                                : "Atenção: A Autenticação de 2 Fatores será obrigatória em " + restantes + " acesso(s).";
                        model.addAttribute("alertaMfa", mensagem);
                    }
                }
            });
        }
    }
}