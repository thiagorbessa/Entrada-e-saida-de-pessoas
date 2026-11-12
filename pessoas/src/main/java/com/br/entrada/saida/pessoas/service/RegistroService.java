package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.exception.RecursoNaoEncontradoException;
import com.br.entrada.saida.pessoas.exception.RegraNegocioException;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.PessoaRepository;
import com.br.entrada.saida.pessoas.repository.RegistroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final PessoaRepository pessoaRepository;

    @Transactional
    public void registrarEntrada(UUID pessoaId, Usuario operador) {
        if (pessoaId == null) {
            throw new RegraNegocioException("Identificador da pessoa é obrigatório.");
        }

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada."));

        boolean existeAberto = registroRepository
                .existsByPessoaIdAndHoraSaidaIsNull(pessoaId);

        if (existeAberto) {
            throw new RegraNegocioException("Pessoa já possui uma entrada aberta no sistema.");
        }

        Registro registro = new Registro();
        registro.setPessoa(pessoa);
        registro.setHoraEntrada(LocalDateTime.now());

        // AUDITORIA: Quem está realizando a entrada
        registro.setUsuarioEntrada(operador);

        // Salva o estado atual da pessoa no momento da entrada
        registro.setUnidade(pessoa.getUnidade());
        registro.setSetor(pessoa.getSetor());
        registro.setAndar(pessoa.getAndar());
        registro.setPaciente(pessoa.getPaciente());

        registroRepository.save(registro);
    }

    @Transactional
    public void registrarSaida(UUID registroId, Usuario operador) {
        if (registroId == null) {
            throw new RegraNegocioException("Identificador do registro é obrigatório.");
        }

        Registro registro = registroRepository.findById(registroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de entrada não encontrado."));

        if (registro.getHoraSaida() != null) {
            throw new RegraNegocioException("Este registro já foi finalizado anteriormente.");
        }

        registro.setHoraSaida(LocalDateTime.now());
        registro.setUsuarioSaida(operador);

        registroRepository.save(registro);
    }

    public List<Registro> buscarRegistrosAbertos() {
        return registroRepository.findByHoraSaidaIsNull();
    }

    @Transactional(readOnly = true)
    public Optional<Registro> buscarRegistroAbertoPorPessoa(UUID pessoaId) {
        return registroRepository.findByPessoaIdAndHoraSaidaIsNull(pessoaId);
    }

    // Método para buscar o histórico de registros da pessoa de forma paginada
    @Transactional(readOnly = true)
    public Page<Registro> buscarHistoricoPorPessoaPaginado(UUID pessoaId, Pageable pageable) {
        return registroRepository.findByPessoaIdOrderByHoraEntradaDesc(pessoaId, pageable);
    }

    // Busca o último registro cadastrado da pessoa (aberto ou já fechado)
    @Transactional(readOnly = true)
    public Registro buscarUltimoRegistroDaPessoa(UUID pessoaId) {
        return registroRepository.findFirstByPessoaIdOrderByHoraEntradaDesc(pessoaId)
                .orElse(null);
    }
}