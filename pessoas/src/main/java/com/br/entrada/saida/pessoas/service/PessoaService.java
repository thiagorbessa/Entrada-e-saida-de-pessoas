package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.dto.PessoaRequestDTO;
import com.br.entrada.saida.pessoas.exception.CpfPessoaDuplicadoException;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final AuditoriaService auditoriaService;

    public boolean isAdmin(Usuario usuario) {
        if (usuario == null || usuario.getPerfil() == null) return false;
        String perfil = usuario.getPerfil();
        return "ROLE_GERAL".equals(perfil) || "ROLE_ADMIN".equals(perfil);
    }

    // Listagem paginada padrão com suporte a 100 itens por página
    @Transactional(readOnly = true)
    public Page<Pessoa> listarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        if (isAdmin(usuario)) {
            return pessoaRepository.findAll(pageable);
        }
        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return Page.empty(pageable);
        }
        return pessoaRepository.findByUnidadeIn(usuario.getUnidades(), pageable);
    }

    // Mantido para compatibilidade caso algum outro lugar chame sem paginação
    @Transactional(readOnly = true)
    public List<Pessoa> listarPorUsuario(Usuario usuario) {
        if (isAdmin(usuario)) {
            return pessoaRepository.findAll();
        }
        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return List.of();
        }
        return pessoaRepository.findByUnidadeIn(usuario.getUnidades());
    }

    @Transactional(readOnly = true)
    public List<Pessoa> buscarPorTermoEUsuario(String termo, Usuario usuario) {
        if (termo == null || termo.isBlank()) {
            return listarPorUsuario(usuario);
        }

        if (isAdmin(usuario)) {
            return pessoaRepository.buscarPorTermo(termo);
        }

        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return List.of();
        }
        return pessoaRepository.buscarPorTermoEUnidades(termo, usuario.getUnidades());
    }

    @Transactional(readOnly = true)
    public Pessoa buscarPorId(UUID id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));
    }

    @Transactional
    public void salvar(PessoaRequestDTO dto, Usuario usuarioLogado) {
        boolean administrador = isAdmin(usuarioLogado);

        // Validação da unidade informada no formulário
        if (!administrador) {
            if (usuarioLogado == null || usuarioLogado.getUnidades() == null || dto.getUnidade() == null
                    || !usuarioLogado.getUnidades().contains(dto.getUnidade())) {
                throw new SecurityException("Você não tem permissão para cadastrar ou vincular pessoas nesta unidade.");
            }
        }

        String cpfLimpo = dto.getCpf() != null ? dto.getCpf().replaceAll("[^\\d]", "") : null;
        boolean cpfDuplicado = false;
        UUID pessoaId = (dto.getId() != null && !dto.getId().isBlank()) ? UUID.fromString(dto.getId()) : null;

        if (cpfLimpo != null && !cpfLimpo.isBlank()) {
            if (pessoaId == null) {
                cpfDuplicado = pessoaRepository.existsByCpf(cpfLimpo);
            } else {
                Pessoa pessoaExistente = pessoaRepository.findByCpf(cpfLimpo).orElse(null);
                if (pessoaExistente != null && !pessoaExistente.getId().equals(pessoaId)) {
                    cpfDuplicado = true;
                }
            }
        }

        if (cpfDuplicado) {
            throw new CpfPessoaDuplicadoException("CPF já cadastrado no sistema.");
        }

        Pessoa pessoa;
        boolean isEdicao = (pessoaId != null);
        String detalhesAuditoria;

        if (isEdicao) {
            pessoa = buscarPorId(pessoaId);

            // Validação de unidade na edição para usuário comum
            if (!administrador) {
                if (pessoa.getUnidade() != null && !usuarioLogado.getUnidades().contains(pessoa.getUnidade())) {
                    throw new SecurityException("Você não tem permissão para alterar o cadastro desta pessoa (unidade diferente).");
                }
                if (!usuarioLogado.getUnidades().contains(dto.getUnidade())) {
                    throw new SecurityException("Você não pode transferir este cadastro para uma unidade sem permissão.");
                }
            }

            detalhesAuditoria = auditoriaService.compararEDetalharPessoa(pessoa, dto);
        } else {
            pessoa = new Pessoa();
            detalhesAuditoria = "Cadastrou a pessoa na unidade " + dto.getUnidade();
        }

        String telLimpo = dto.getTelefone() != null ? dto.getTelefone().replaceAll("[^\\d]", "") : null;

        pessoa.setCpf(cpfLimpo);
        pessoa.setIdentidade(dto.getIdentidade());
        pessoa.setNome(dto.getNome());
        pessoa.setTelefone(telLimpo);
        pessoa.setUnidade(dto.getUnidade());
        pessoa.setSetor(dto.getSetor());
        pessoa.setAndar(dto.getAndar());
        pessoa.setPaciente(dto.getPaciente());
        pessoa.setObservacao(dto.getObservacao());
        pessoa.setUsuarioResponsavel(usuarioLogado);

        pessoaRepository.save(pessoa);

        String acao = isEdicao ? "EDICAO" : "CRIACAO";
        auditoriaService.registrar(usuarioLogado, acao, "PESSOA", pessoa.getId(), pessoa.getNome(), detalhesAuditoria);
    }

    @Transactional
    public void deletar(UUID id, Usuario usuarioLogado) {
        if (!isAdmin(usuarioLogado)) {
            throw new SecurityException("Acesso negado: apenas administradores (Geral ou Sistema) podem excluir registros.");
        }

        Pessoa pessoa = buscarPorId(id);
        auditoriaService.registrar(usuarioLogado, "EXCLUSAO", "PESSOA", pessoa.getId(), pessoa.getNome(), "Excluiu o registro da pessoa");
        pessoaRepository.delete(pessoa);
    }
}