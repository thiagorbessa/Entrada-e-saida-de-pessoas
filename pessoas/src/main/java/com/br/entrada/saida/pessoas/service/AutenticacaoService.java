package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        // Busca o usuário no banco pelo CPF (que é o nosso login)
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o CPF: " + cpf));

        // CORREÇÃO: Utiliza .authorities para injetar a Role exatamente como está gravada no banco (ex: "ROLE_GERAL")
        return User.builder()
                .username(usuario.getCpf())
                .password(usuario.getSenha())
                .authorities(usuario.getPerfil())
                .build();
    }
}