package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.LogAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final LogAuditoriaRepository logRepository;

    @Transactional
    public void registrar(Usuario usuario, String acao, String entidade, UUID entidadeId, String descricao, String detalhes) {
        if (usuario == null) return;
        LogAuditoria log = new LogAuditoria();
        log.setUsuarioCpf(usuario.getCpf());
        log.setUsuarioNome(usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : usuario.getCpf());
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setEntidadeDescricao(descricao);
        log.setDetalhes(detalhes);
        logRepository.save(log);
    }

    public String compararEDetalharPessoa(Pessoa antiga, com.br.entrada.saida.pessoas.dto.PessoaRequestDTO nova) {
        List<String> mudancas = new ArrayList<>();

        if (!Objects.equals(antiga.getNome(), nova.getNome())) {
            mudancas.add("Nome: '" + antiga.getNome() + "' -> '" + nova.getNome() + "'");
        }
        if (!Objects.equals(antiga.getCpf(), nova.getCpf())) {
            mudancas.add("CPF: '" + antiga.getCpf() + "' -> '" + nova.getCpf() + "'");
        }
        if (!Objects.equals(antiga.getIdentidade(), nova.getIdentidade())) {
            mudancas.add("Identidade: '" + antiga.getIdentidade() + "' -> '" + nova.getIdentidade() + "'");
        }
        if (!Objects.equals(antiga.getTelefone(), nova.getTelefone())) {
            mudancas.add("Telefone: '" + antiga.getTelefone() + "' -> '" + nova.getTelefone() + "'");
        }
        if (!Objects.equals(antiga.getUnidade(), nova.getUnidade())) {
            mudancas.add("Unidade: '" + antiga.getUnidade() + "' -> '" + nova.getUnidade() + "'");
        }
        if (!Objects.equals(antiga.getSetor(), nova.getSetor())) {
            mudancas.add("Setor: '" + antiga.getSetor() + "' -> '" + nova.getSetor() + "'");
        }
        if (!Objects.equals(antiga.getAndar(), nova.getAndar())) {
            mudancas.add("Andar: '" + antiga.getAndar() + "' -> '" + nova.getAndar() + "'");
        }
        if (!Objects.equals(antiga.getPaciente(), nova.getPaciente())) {
            mudancas.add("Paciente: '" + antiga.getPaciente() + "' -> '" + nova.getPaciente() + "'");
        }
        if (!Objects.equals(antiga.getObservacao(), nova.getObservacao())) {
            mudancas.add("Observação alterada");
        }

        return mudancas.isEmpty() ? "Nenhum campo alterado" : String.join("; ", mudancas);
    }

    public String compararEDetalharUsuario(Usuario antigo, String novoNome, String novoPerfil, List<?> novasUnidades) {
        List<String> mudancas = new ArrayList<>();

        if (!Objects.equals(antigo.getNomeCompleto(), novoNome)) {
            mudancas.add("Nome: '" + antigo.getNomeCompleto() + "' -> '" + novoNome + "'");
        }
        if (!Objects.equals(antigo.getPerfil(), novoPerfil)) {
            mudancas.add("Perfil: '" + antigo.getPerfil() + "' -> '" + novoPerfil + "'");
        }

        List<String> unidadesAntigasStr = antigo.getUnidades() != null ? antigo.getUnidades().stream().map(Enum::name).sorted().collect(Collectors.toList()) : List.of();
        List<String> unidadesNovasStr = novasUnidades != null ? novasUnidades.stream().map(Object::toString).sorted().collect(Collectors.toList()) : List.of();

        if (!unidadesAntigasStr.equals(unidadesNovasStr)) {
            mudancas.add("Unidades: " + unidadesAntigasStr + " -> " + unidadesNovasStr);
        }

        return mudancas.isEmpty() ? "Nenhum campo alterado" : String.join("; ", mudancas);
    }

    public List<LogAuditoria> listarTodos() {
        return logRepository.findAllByOrderByDataHoraDesc();
    }
}