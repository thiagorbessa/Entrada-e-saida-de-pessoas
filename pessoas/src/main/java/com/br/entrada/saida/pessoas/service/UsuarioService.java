package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.dto.UsuarioRequestDTO;
import com.br.entrada.saida.pessoas.exception.CpfDuplicadoException;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaService auditoriaService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario salvar(UsuarioRequestDTO dto, Usuario usuarioLogado) {
        // Limpa o CPF para manter apenas os números (remove pontos e traços)
        String cpfLimpo = dto.getCpf() != null ? dto.getCpf().replaceAll("[^0-9]", "") : "";

        // Validação de CPF duplicado na criação ou edição usando o CPF limpo
        usuarioRepository.findByCpf(cpfLimpo).ifPresent(u -> {
            if (dto.getId() == null || !u.getId().equals(dto.getId())) {
                throw new CpfDuplicadoException("Já existe um usuário cadastrado com este CPF.");
            }
        });

        Usuario usuario;
        boolean isEdicao = (dto.getId() != null);
        String detalhesAuditoria = "";

        if (isEdicao) {
            usuario = buscarPorId(dto.getId());

            // Pega o estado anterior para comparar o antes e depois
            detalhesAuditoria = auditoriaService.compararEDetalharUsuario(usuario, dto.getNomeCompleto(), dto.getPerfil(), dto.getUnidades() != null ? List.copyOf(dto.getUnidades()) : List.of());

            usuario.setNomeCompleto(dto.getNomeCompleto());
            usuario.setCpf(cpfLimpo); // Salva limpo

            if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.getSenha().trim()));
            }
            if (dto.getPerfil() != null) {
                usuario.setPerfil(dto.getPerfil());
            }

            usuario.getUnidades().clear();
            usuarioRepository.flush();

            if (dto.getUnidades() != null) {
                usuario.getUnidades().addAll(dto.getUnidades());
            }
        } else {
            if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("A senha é obrigatória para novos usuários.");
            }

            usuario = new Usuario();
            usuario.setNomeCompleto(dto.getNomeCompleto());
            usuario.setCpf(cpfLimpo); // Salva limpo
            usuario.setSenha(passwordEncoder.encode(dto.getSenha().trim()));
            usuario.setPerfil(dto.getPerfil() != null ? dto.getPerfil() : "ROLE_USER");

            if (dto.getUnidades() != null) {
                usuario.getUnidades().addAll(dto.getUnidades());
            }

            detalhesAuditoria = "Criou o utilizador com perfil " + usuario.getPerfil();
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Registra a auditoria após salvar
        String acao = isEdicao ? "EDICAO" : "CRIACAO";
        UUID uuidLog = usuarioSalvo.getId() != null ? UUID.nameUUIDFromBytes(usuarioSalvo.getId().toString().getBytes()) : null;
        auditoriaService.registrar(usuarioLogado, acao, "USUARIO", uuidLog, usuarioSalvo.getNomeCompleto(), detalhesAuditoria);

        return usuarioSalvo;
    }

    @Transactional
    public void deletar(Long id, Usuario usuarioLogado) {
        Usuario usuario = buscarPorId(id);

        // Impede que o usuário delete a si mesmo
        if (usuarioLogado != null && usuarioLogado.getId().equals(usuario.getId())) {
            throw new SecurityException("Você não pode excluir o seu próprio usuário.");
        }

        UUID uuidLog = usuario.getId() != null ? UUID.nameUUIDFromBytes(usuario.getId().toString().getBytes()) : null;
        auditoriaService.registrar(usuarioLogado, "EXCLUSAO", "USUARIO",
                uuidLog, usuario.getNomeCompleto(),
                "Excluiu o registo do utilizador");

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public void alterarSenha(Usuario usuarioLogado, String senhaAntiga, String senhaNova) {
        if (senhaAntiga == null || senhaAntiga.trim().isEmpty() || senhaNova == null || senhaNova.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha antiga e a nova senha são obrigatórias.");
        }

        if (!passwordEncoder.matches(senhaAntiga.trim(), usuarioLogado.getSenha())) {
            throw new SecurityException("A senha antiga informada está incorreta.");
        }

        usuarioLogado.setSenha(passwordEncoder.encode(senhaNova.trim()));
        usuarioRepository.save(usuarioLogado);
    }
}