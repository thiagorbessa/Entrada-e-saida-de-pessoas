package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.dto.AcompanhanteRecord;
import com.br.entrada.saida.pessoas.dto.PacienteInternadoRecord;
import com.br.entrada.saida.pessoas.exception.AcessoNegadoException;
import com.br.entrada.saida.pessoas.model.*;
import com.br.entrada.saida.pessoas.repository.AcompanhanteRepository;
import com.br.entrada.saida.pessoas.repository.LogAuditoriaRepository;
import com.br.entrada.saida.pessoas.repository.PacienteInternadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternacaoService {

    public static final String ENTIDADE_PACIENTE = "PACIENTE_INTERNADO";
    public static final String ENTIDADE_ACOMPANHANTE = "ACOMPANHANTE";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final PacienteInternadoRepository pacienteRepository;
    private final AcompanhanteRepository acompanhanteRepository;
    private final AuditoriaService auditoriaService;
    private final LogAuditoriaRepository logAuditoriaRepository;

    // ==========================================
    // CONTROLE DE PERMISSÃO POR UNIDADE
    // ==========================================
    public boolean isAdmin(Usuario usuario) {
        if (usuario == null || usuario.getPerfil() == null) return false;
        String perfil = usuario.getPerfil();
        return "ROLE_GERAL".equals(perfil) || "ROLE_ADMIN".equals(perfil);
    }

    public void validarPermissaoUnidade(Usuario operador, Unidade unidade) {
        if (isAdmin(operador)) return;
        if (operador == null || operador.getUnidades() == null || unidade == null
                || !operador.getUnidades().contains(unidade)) {
            throw new AcessoNegadoException("Você não tem permissão para visualizar ou movimentar cadastros desta unidade.");
        }
    }

    @Transactional(readOnly = true)
    public List<PacienteInternado> listarPacientesPorUsuario(Usuario usuario) {
        if (isAdmin(usuario)) {
            return pacienteRepository.findAllByOrderByHoraEntradaDesc();
        }
        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return List.of();
        }
        return pacienteRepository.findByUnidadeInOrderByHoraEntradaDesc(usuario.getUnidades());
    }

    @Transactional(readOnly = true)
    public List<PacienteInternado> listarPacientesAtivosPorUsuario(Usuario usuario) {
        if (isAdmin(usuario)) {
            return pacienteRepository.findByHoraSaidaIsNullOrderByNomeAsc();
        }
        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return List.of();
        }
        return pacienteRepository.findByUnidadeInAndHoraSaidaIsNullOrderByNomeAsc(usuario.getUnidades());
    }

    @Transactional(readOnly = true)
    public List<Acompanhante> listarAcompanhantesPorUsuario(Usuario usuario) {
        if (isAdmin(usuario)) {
            return acompanhanteRepository.findAllByOrderByHoraEntradaDesc();
        }
        if (usuario == null || usuario.getUnidades() == null || usuario.getUnidades().isEmpty()) {
            return List.of();
        }
        return acompanhanteRepository.findByUnidadeInOrderByHoraEntradaDesc(usuario.getUnidades());
    }

    private void validarLimitesAcompanhante(PacienteInternado paciente) {
        if (paciente == null || !paciente.isInternadoAtivo()) {
            throw new IllegalStateException("Acesso bloqueado: O paciente vinculado já recebeu Alta ou Óbito e não está internado.");
        }

        // Máximo de 2 acompanhantes simultâneos (sem saída registrada)
        long simultaneos = acompanhanteRepository.countByPacienteAndHoraSaidaIsNull(paciente);
        if (simultaneos >= 2) {
            throw new IllegalStateException("Acesso bloqueado: O paciente já possui 2 acompanhantes ativos no momento. Registre uma saída antes de liberar nova entrada.");
        }

        // Máximo de 5 entradas de acompanhantes no mesmo dia para este paciente
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDoDia = LocalDate.now().atTime(LocalTime.MAX);
        long acessosNoDia = acompanhanteRepository.countByPacienteAndHoraEntradaBetween(paciente, inicioDoDia, fimDoDia);

        if (acessosNoDia >= 5) {
            throw new IllegalStateException("Acesso bloqueado: Limite diário de 5 entradas de acompanhantes atingido para este paciente.");
        }
    }

    // ==========================================
    // PACIENTE INTERNADO
    // ==========================================
    @Transactional
    public PacienteInternado registrarEntradaPaciente(PacienteInternadoRecord dto, Usuario operador) {
        validarPermissaoUnidade(operador, dto.unidade());

        String cpfLimpo = dto.cpf() != null ? dto.cpf().replaceAll("\\D", "") : null;

        Optional<PacienteInternado> existenteOpt = pacienteRepository.findByCpf(cpfLimpo);
        if (existenteOpt.isPresent()) {
            PacienteInternado existente = existenteOpt.get();

            validarPermissaoUnidade(operador, existente.getUnidade());

            if (existente.isObito()) {
                throw new IllegalStateException("Operação bloqueada: Este paciente possui registro finalizado por Óbito.");
            }
            if (existente.isInternadoAtivo()) {
                throw new IllegalStateException("Este paciente já possui uma internação ativa (sem registro de saída).");
            }

            existente.setNome(dto.nome());
            existente.setTelefone(dto.telefone());
            existente.setUnidade(dto.unidade());
            existente.setAndar(dto.andar());
            existente.setLeito(dto.leito());
            existente.setObservacao(dto.observacao());
            existente.setStatus(StatusInternacao.INTERNADO);
            existente.setHoraEntrada(LocalDateTime.now());
            existente.setHoraSaida(null);
            existente.setUsuarioEntrada(operador);
            existente.setUsuarioSaida(null);

            PacienteInternado salvo = pacienteRepository.save(existente);

            auditoriaService.registrar(operador, "ENTRADA", ENTIDADE_PACIENTE, salvo.getId(), salvo.getNome(),
                    "Nova entrada de internação | Unidade: " + salvo.getUnidade()
                            + " | Andar: " + salvo.getAndar()
                            + " | Leito: " + salvo.getLeito());
            return salvo;
        }

        PacienteInternado paciente = new PacienteInternado();
        paciente.setNome(dto.nome());
        paciente.setCpf(cpfLimpo);
        paciente.setTelefone(dto.telefone());
        paciente.setUnidade(dto.unidade());
        paciente.setAndar(dto.andar());
        paciente.setLeito(dto.leito());
        paciente.setObservacao(dto.observacao());
        paciente.setStatus(StatusInternacao.INTERNADO);
        paciente.setHoraEntrada(LocalDateTime.now());
        paciente.setUsuarioEntrada(operador);

        PacienteInternado salvo = pacienteRepository.save(paciente);

        auditoriaService.registrar(operador, "ENTRADA", ENTIDADE_PACIENTE, salvo.getId(), salvo.getNome(),
                "Entrada de internação | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    @Transactional
    public PacienteInternado registrarNovaEntradaPaciente(UUID idPaciente, Usuario operador) {
        PacienteInternado paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        validarPermissaoUnidade(operador, paciente.getUnidade());

        if (paciente.isObito()) {
            throw new IllegalStateException("Operação bloqueada: Registro finalizado por Óbito não permite nova entrada.");
        }
        if (paciente.isInternadoAtivo()) {
            throw new IllegalStateException("Este paciente já está internado no momento.");
        }

        paciente.setStatus(StatusInternacao.INTERNADO);
        paciente.setHoraEntrada(LocalDateTime.now());
        paciente.setHoraSaida(null);
        paciente.setUsuarioEntrada(operador);
        paciente.setUsuarioSaida(null);

        PacienteInternado salvo = pacienteRepository.save(paciente);

        auditoriaService.registrar(operador, "ENTRADA", ENTIDADE_PACIENTE, salvo.getId(), salvo.getNome(),
                "Nova entrada após Alta | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    @Transactional
    public PacienteInternado registrarSaidaPaciente(UUID idPaciente, StatusInternacao statusSaida, Usuario operador) {
        PacienteInternado paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        validarPermissaoUnidade(operador, paciente.getUnidade());

        if (!paciente.isInternadoAtivo()) {
            throw new IllegalStateException("Este paciente já possui registro de saída.");
        }
        if (statusSaida == null || statusSaida == StatusInternacao.INTERNADO) {
            throw new IllegalArgumentException("Selecione um motivo de saída válido (Alta ou Óbito).");
        }

        LocalDateTime agora = LocalDateTime.now();

        if (paciente.getAcompanhantes() != null) {
            for (Acompanhante acomp : paciente.getAcompanhantes()) {
                if (acomp.getHoraSaida() == null) {
                    acomp.setHoraSaida(agora);
                    acomp.setUsuarioSaida(operador);
                    acompanhanteRepository.save(acomp);

                    auditoriaService.registrar(operador, "SAIDA", ENTIDADE_ACOMPANHANTE, acomp.getId(), acomp.getNome(),
                            "Saída automática por " + statusSaida.getDescricao() + " do paciente " + paciente.getNome()
                                    + " | Entrada: " + (acomp.getHoraEntrada() != null ? acomp.getHoraEntrada().format(FMT) : "-")
                                    + " | Saída: " + agora.format(FMT));
                }
            }
        }

        paciente.setStatus(statusSaida);
        paciente.setHoraSaida(agora);
        paciente.setUsuarioSaida(operador);

        PacienteInternado salvo = pacienteRepository.save(paciente);

        auditoriaService.registrar(operador, "SAIDA (" + statusSaida.getDescricao().toUpperCase() + ")",
                ENTIDADE_PACIENTE, salvo.getId(), salvo.getNome(),
                "Motivo: " + statusSaida.getDescricao()
                        + " | Entrada: " + (salvo.getHoraEntrada() != null ? salvo.getHoraEntrada().format(FMT) : "-")
                        + " | Saída: " + agora.format(FMT)
                        + " | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    // ==========================================
    // ACOMPANHANTE
    // ==========================================
    @Transactional
    public Acompanhante registrarEntradaAcompanhante(AcompanhanteRecord dto, Usuario operador) {
        if (dto.pacienteId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o paciente vinculado ao acompanhante.");
        }

        validarPermissaoUnidade(operador, dto.unidade());

        PacienteInternado paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        validarPermissaoUnidade(operador, paciente.getUnidade());
        validarLimitesAcompanhante(paciente);

        String cpfLimpo = dto.cpf() != null ? dto.cpf().replaceAll("\\D", "") : null;
        if (acompanhanteRepository.existsByCpfAndHoraSaidaIsNull(cpfLimpo)) {
            throw new IllegalStateException("Este acompanhante já possui uma entrada ativa sem registro de saída.");
        }

        Acompanhante acompanhante = acompanhanteRepository
                .findFirstByCpfAndPacienteIdOrderByHoraEntradaDesc(cpfLimpo, paciente.getId())
                .orElseGet(Acompanhante::new);

        acompanhante.setNome(dto.nome());
        acompanhante.setCpf(cpfLimpo);
        acompanhante.setTelefone(dto.telefone());
        acompanhante.setUnidade(dto.unidade());
        acompanhante.setAndar(dto.andar());
        acompanhante.setLeito(dto.leito());
        acompanhante.setPaciente(paciente);
        acompanhante.setHoraEntrada(LocalDateTime.now());
        acompanhante.setHoraSaida(null);
        acompanhante.setUsuarioEntrada(operador);
        acompanhante.setUsuarioSaida(null);

        Acompanhante salvo = acompanhanteRepository.save(acompanhante);

        auditoriaService.registrar(operador, "ENTRADA", ENTIDADE_ACOMPANHANTE, salvo.getId(), salvo.getNome(),
                "Paciente vinculado: " + paciente.getNome()
                        + " | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    @Transactional
    public Acompanhante registrarNovaEntradaAcompanhante(UUID idAcompanhante, Usuario operador) {
        Acompanhante acompanhante = acompanhanteRepository.findById(idAcompanhante)
                .orElseThrow(() -> new IllegalArgumentException("Acompanhante não encontrado."));

        validarPermissaoUnidade(operador, acompanhante.getUnidade());

        if (acompanhante.getHoraSaida() == null) {
            throw new IllegalStateException("Este acompanhante já possui uma entrada ativa.");
        }

        PacienteInternado paciente = acompanhante.getPaciente();
        validarPermissaoUnidade(operador, paciente.getUnidade());
        validarLimitesAcompanhante(paciente);

        if (acompanhanteRepository.existsByCpfAndHoraSaidaIsNull(acompanhante.getCpf())) {
            throw new IllegalStateException("Este acompanhante já possui uma entrada ativa em outro vínculo.");
        }

        acompanhante.setUnidade(paciente.getUnidade());
        acompanhante.setAndar(paciente.getAndar());
        acompanhante.setLeito(paciente.getLeito());
        acompanhante.setHoraEntrada(LocalDateTime.now());
        acompanhante.setHoraSaida(null);
        acompanhante.setUsuarioEntrada(operador);
        acompanhante.setUsuarioSaida(null);

        Acompanhante salvo = acompanhanteRepository.save(acompanhante);

        auditoriaService.registrar(operador, "ENTRADA", ENTIDADE_ACOMPANHANTE, salvo.getId(), salvo.getNome(),
                "Nova entrada | Paciente vinculado: " + paciente.getNome()
                        + " | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    @Transactional
    public Acompanhante registrarSaidaAcompanhante(UUID idAcompanhante, Usuario operador) {
        Acompanhante acompanhante = acompanhanteRepository.findById(idAcompanhante)
                .orElseThrow(() -> new IllegalArgumentException("Acompanhante não encontrado."));

        validarPermissaoUnidade(operador, acompanhante.getUnidade());

        if (acompanhante.getHoraSaida() != null) {
            throw new IllegalStateException("Este acompanhante já possui registro de saída.");
        }

        LocalDateTime agora = LocalDateTime.now();
        acompanhante.setHoraSaida(agora);
        acompanhante.setUsuarioSaida(operador);
        Acompanhante salvo = acompanhanteRepository.save(acompanhante);

        auditoriaService.registrar(operador, "SAIDA", ENTIDADE_ACOMPANHANTE, salvo.getId(), salvo.getNome(),
                "Paciente vinculado: " + (salvo.getPaciente() != null ? salvo.getPaciente().getNome() : "-")
                        + " | Entrada: " + (salvo.getHoraEntrada() != null ? salvo.getHoraEntrada().format(FMT) : "-")
                        + " | Saída: " + agora.format(FMT)
                        + " | Unidade: " + salvo.getUnidade()
                        + " | Andar: " + salvo.getAndar()
                        + " | Leito: " + salvo.getLeito());
        return salvo;
    }

    // ==========================================
    // CONSULTA DE HISTÓRICO VIA LOG DE AUDITORIA
    // ==========================================
    @Transactional(readOnly = true)
    public List<LogAuditoria> buscarLogsPaciente(UUID pacienteId) {
        return logAuditoriaRepository.findByEntidadeIdAndEntidadeOrderByDataHoraDesc(pacienteId, ENTIDADE_PACIENTE);
    }

    @Transactional(readOnly = true)
    public List<LogAuditoria> buscarLogsAcompanhante(UUID acompanhanteId) {
        return logAuditoriaRepository.findByEntidadeIdAndEntidadeOrderByDataHoraDesc(acompanhanteId, ENTIDADE_ACOMPANHANTE);
    }

    private boolean podeExcluir(Usuario operador) {
        if (operador == null || operador.getPerfil() == null) return false;
        String perfil = operador.getPerfil();
        return "ROLE_SISTEMA".equals(perfil) || "ROLE_GERAL".equals(perfil)
                || "SISTEMA".equals(perfil) || "GERAL".equals(perfil);
    }

    // ==========================================
    // EDITAR E EXCLUIR PACIENTE INTERNADO
    // ==========================================
    @Transactional
    public PacienteInternado editarPaciente(UUID id, PacienteInternadoRecord dto, Usuario operador) {
        PacienteInternado paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        validarPermissaoUnidade(operador, paciente.getUnidade());
        validarPermissaoUnidade(operador, dto.unidade());

        String cpfLimpo = dto.cpf() != null ? dto.cpf().replaceAll("\\D", "") : null;
        if (cpfLimpo != null && !cpfLimpo.equals(paciente.getCpf())) {
            pacienteRepository.findByCpf(cpfLimpo).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new IllegalStateException("Já existe outro paciente cadastrado com este CPF.");
                }
            });
        }

        String detalhes = String.format(
                "Edição | Nome: '%s' -> '%s' | CPF: '%s' -> '%s' | Unidade: %s -> %s | Andar: '%s' -> '%s' | Leito: '%s' -> '%s'",
                paciente.getNome(), dto.nome(),
                paciente.getCpf(), cpfLimpo,
                paciente.getUnidade(), dto.unidade(),
                paciente.getAndar(), dto.andar(),
                paciente.getLeito(), dto.leito()
        );

        paciente.setNome(dto.nome());
        paciente.setCpf(cpfLimpo);
        paciente.setTelefone(dto.telefone());
        paciente.setUnidade(dto.unidade());
        paciente.setAndar(dto.andar());
        paciente.setLeito(dto.leito());
        paciente.setObservacao(dto.observacao());

        PacienteInternado salvo = pacienteRepository.save(paciente);
        auditoriaService.registrar(operador, "EDICAO", ENTIDADE_PACIENTE, salvo.getId(), salvo.getNome(), detalhes);
        return salvo;
    }

    @Transactional
    public void excluirPaciente(UUID id, Usuario operador) {
        if (!podeExcluir(operador)) {
            throw new SecurityException("Acesso negado: Apenas Administrador do Sistema ou Administrador Geral pode apagar registros.");
        }

        PacienteInternado paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        validarPermissaoUnidade(operador, paciente.getUnidade());

        String nome = paciente.getNome();
        String cpf = paciente.getCpf();
        pacienteRepository.delete(paciente);

        auditoriaService.registrar(operador, "EXCLUSAO", ENTIDADE_PACIENTE, id, nome,
                "Paciente internado excluído (CPF: " + cpf + ")");
    }

    // ==========================================
    // EDITAR E EXCLUIR ACOMPANHANTE
    // ==========================================
    @Transactional
    public Acompanhante editarAcompanhante(UUID id, AcompanhanteRecord dto, Usuario operador) {
        Acompanhante acompanhante = acompanhanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acompanhante não encontrado."));

        validarPermissaoUnidade(operador, acompanhante.getUnidade());
        validarPermissaoUnidade(operador, dto.unidade());

        String cpfLimpo = dto.cpf() != null ? dto.cpf().replaceAll("\\D", "") : null;

        PacienteInternado paciente = acompanhante.getPaciente();
        if (dto.pacienteId() != null && (paciente == null || !paciente.getId().equals(dto.pacienteId()))) {
            paciente = pacienteRepository.findById(dto.pacienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Paciente vinculado não encontrado."));
            validarPermissaoUnidade(operador, paciente.getUnidade());
            acompanhante.setPaciente(paciente);
        }

        String detalhes = String.format(
                "Edição | Nome: '%s' -> '%s' | CPF: '%s' -> '%s' | Unidade: %s -> %s | Andar: '%s' -> '%s' | Leito: '%s' -> '%s'",
                acompanhante.getNome(), dto.nome(),
                acompanhante.getCpf(), cpfLimpo,
                acompanhante.getUnidade(), dto.unidade(),
                acompanhante.getAndar(), dto.andar(),
                acompanhante.getLeito(), dto.leito()
        );

        acompanhante.setNome(dto.nome());
        acompanhante.setCpf(cpfLimpo);
        acompanhante.setTelefone(dto.telefone());
        acompanhante.setUnidade(dto.unidade());
        acompanhante.setAndar(dto.andar());
        acompanhante.setLeito(dto.leito());

        Acompanhante salvo = acompanhanteRepository.save(acompanhante);
        auditoriaService.registrar(operador, "EDICAO", ENTIDADE_ACOMPANHANTE, salvo.getId(), salvo.getNome(), detalhes);
        return salvo;
    }

    @Transactional
    public void excluirAcompanhante(UUID id, Usuario operador) {
        if (!podeExcluir(operador)) {
            throw new SecurityException("Acesso negado: Apenas Administrador do Sistema ou Administrador Geral pode apagar registros.");
        }

        Acompanhante acompanhante = acompanhanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acompanhante não encontrado."));

        validarPermissaoUnidade(operador, acompanhante.getUnidade());

        String nome = acompanhante.getNome();
        String cpf = acompanhante.getCpf();
        acompanhanteRepository.delete(acompanhante);

        auditoriaService.registrar(operador, "EXCLUSAO", ENTIDADE_ACOMPANHANTE, id, nome,
                "Acompanhante excluído (CPF: " + cpf + ")");
    }
}