package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.dto.UsuarioRequestDTO;
import com.br.entrada.saida.pessoas.exception.AcessoNegadoException;
import com.br.entrada.saida.pessoas.exception.CpfDuplicadoException;
import com.br.entrada.saida.pessoas.exception.RecursoNaoEncontradoException;
import com.br.entrada.saida.pessoas.exception.RegraNegocioException;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
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
            detalhesAuditoria = auditoriaService.compararEDetalharUsuario(
                    usuario,
                    dto.getNomeCompleto(),
                    dto.getPerfil(),
                    dto.getUnidades() != null ? List.copyOf(dto.getUnidades()) : List.of()
            );

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
                throw new RegraNegocioException("A senha é obrigatória para novos usuários.");
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
        auditoriaService.registrar(usuarioLogado, acao, "USUARIO", usuarioSalvo.getId(), usuarioSalvo.getNomeCompleto(), detalhesAuditoria);

        return usuarioSalvo;
    }

    @Transactional
    public void deletar(UUID id, Usuario usuarioLogado) {
        Usuario usuario = buscarPorId(id);

        // Impede que o usuário delete a si mesmo
        if (usuarioLogado != null && usuarioLogado.getId().equals(usuario.getId())) {
            throw new AcessoNegadoException("Você não pode excluir o seu próprio usuário.");
        }

        String idStr = id.toString();

        // 1. Preserva o nome e CPF nos cadastros de pessoas e registros de entrada/saída antes de desvincular
        jdbcTemplate.update(
                "UPDATE pessoas SET usuario_responsavel_nome = ?, usuario_responsavel_cpf = ? " +
                        "WHERE usuario_id = ? OR usuario_id = UNHEX(REPLACE(?, '-', ''))",
                usuario.getNomeCompleto(), usuario.getCpf(), idStr, idStr
        );
        jdbcTemplate.update(
                "UPDATE registros SET usuario_entrada_nome = ?, usuario_entrada_cpf = ? " +
                        "WHERE usuario_entrada_id = ? OR usuario_entrada_id = UNHEX(REPLACE(?, '-', ''))",
                usuario.getNomeCompleto(), usuario.getCpf(), idStr, idStr
        );
        jdbcTemplate.update(
                "UPDATE registros SET usuario_saida_nome = ?, usuario_saida_cpf = ? " +
                        "WHERE usuario_saida_id = ? OR usuario_saida_id = UNHEX(REPLACE(?, '-', ''))",
                usuario.getNomeCompleto(), usuario.getCpf(), idStr, idStr
        );

        // 2. Desvincula as chaves estrangeiras das tabelas operacionais
        desvincularColunaUsuario("pessoas", "usuario_id", idStr);
        desvincularColunaUsuario("registros", "usuario_entrada_id", idStr);
        desvincularColunaUsuario("registros", "usuario_saida_id", idStr);
        desvincularColunaUsuario("pacientes_internados", "usuario_entrada_id", idStr);
        desvincularColunaUsuario("pacientes_internados", "usuario_saida_id", idStr);
        desvincularColunaUsuario("acompanhantes", "usuario_entrada_id", idStr);
        desvincularColunaUsuario("acompanhantes", "usuario_saida_id", idStr);
        desvincularColunaUsuario("historico_internacao", "usuario_entrada_id", idStr);
        desvincularColunaUsuario("historico_internacao", "usuario_saida_id", idStr);

        // 3. Limpa a coleção de unidades do usuário (tabela usuario_unidades)
        usuario.getUnidades().clear();
        usuarioRepository.saveAndFlush(usuario);

        // 4. Registra a exclusão no log de auditoria
        auditoriaService.registrar(
                usuarioLogado,
                "EXCLUSAO",
                "USUARIO",
                usuario.getId(),
                usuario.getNomeCompleto() + " (CPF: " + usuario.getCpf() + ")",
                "Excluiu o registo do utilizador e preservou o histórico"
        );

        // 5. Exclui o usuário
        usuarioRepository.delete(usuario);
    }

    private void desvincularColunaUsuario(String tabela, String coluna, String idStr) {
        try {
            jdbcTemplate.update(
                    "UPDATE " + tabela + " SET " + coluna + " = NULL " +
                            "WHERE " + coluna + " = ? OR " + coluna + " = UNHEX(REPLACE(?, '-', ''))",
                    idStr, idStr
            );
        } catch (Exception ignored) {
            // Ignora caso alguma tabela ainda não exista no ambiente
        }
    }

    @Transactional
    public void alterarSenha(Usuario usuarioLogado, String senhaAntiga, String senhaNova) {
        if (usuarioLogado == null) {
            throw new AcessoNegadoException("Usuário não autenticado.");
        }

        if (senhaAntiga == null || senhaAntiga.trim().isEmpty() || senhaNova == null || senhaNova.trim().isEmpty()) {
            throw new RegraNegocioException("A senha antiga e a nova senha são obrigatórias.");
        }

        if (!passwordEncoder.matches(senhaAntiga.trim(), usuarioLogado.getSenha())) {
            throw new AcessoNegadoException("A senha antiga informada está incorreta.");
        }

        usuarioLogado.setSenha(passwordEncoder.encode(senhaNova.trim()));
        usuarioRepository.save(usuarioLogado);
    }
}