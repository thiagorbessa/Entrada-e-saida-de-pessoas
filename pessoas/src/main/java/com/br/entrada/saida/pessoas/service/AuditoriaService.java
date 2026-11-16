package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.model.Acompanhante;
import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.PacienteInternado;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
import com.br.entrada.saida.pessoas.model.StatusInternacao;
import com.br.entrada.saida.pessoas.model.Unidade;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.LogAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final LogAuditoriaRepository logRepository;
    private final JdbcTemplate jdbcTemplate;

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

    public Page<LogAuditoria> listarTodosPaginados(Pageable pageable) {
        return logRepository.findAllByOrderByDataHoraDesc(pageable);
    }

    // ==========================================
    // HELPERS DE LEITURA DO ResultSet
    // ==========================================

    private LocalDateTime lerDataHora(ResultSet rs, String... colunas) {
        for (String c : colunas) {
            try {
                Object v = rs.getObject(c);
                if (v == null) continue;
                if (v instanceof Timestamp t) return t.toLocalDateTime();
                if (v instanceof LocalDateTime l) return l;
                return LocalDateTime.parse(v.toString().trim().replace(" ", "T"));
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private String lerString(ResultSet rs, String... colunas) {
        for (String c : colunas) {
            try {
                String v = rs.getString(c);
                if (v != null) return v;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private UUID lerUuid(ResultSet rs, String... colunas) {
        for (String c : colunas) {
            try {
                byte[] bytes = rs.getBytes(c);
                if (bytes == null) continue;
                if (bytes.length == 16) {
                    ByteBuffer bb = ByteBuffer.wrap(bytes);
                    return new UUID(bb.getLong(), bb.getLong());
                }
                return UUID.fromString(new String(bytes, StandardCharsets.UTF_8).trim());
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private Unidade lerUnidade(ResultSet rs) {
        String s = lerString(rs, "unidade");
        if (s == null || s.isBlank()) return null;
        try {
            return Unidade.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    // ==========================================
    // BACKUP DE LOGS
    // ==========================================

    public List<LogAuditoria> listarLogsPorAnoBackup(int ano) {
        String[] possiveisTabelas = {"logs_" + ano, "log_auditoria_" + ano, "auditoria_logs_" + ano};

        for (String nomeTabela : possiveisTabelas) {
            String sql = "SELECT * FROM " + nomeTabela + " ORDER BY id DESC";
            try {
                List<LogAuditoria> resultado = jdbcTemplate.query(sql, (rs, rowNum) -> {
                    LogAuditoria log = new LogAuditoria();
                    UUID id = lerUuid(rs, "id");
                    if (id != null) log.setId(id);
                    log.setDataHora(lerDataHora(rs, "data_hora", "dataHora"));
                    log.setUsuarioCpf(lerString(rs, "usuario_cpf", "usuarioCpf"));
                    log.setUsuarioNome(lerString(rs, "usuario_nome", "usuarioNome"));
                    log.setAcao(lerString(rs, "acao"));
                    log.setEntidade(lerString(rs, "entidade"));
                    log.setEntidadeDescricao(lerString(rs, "entidade_descricao", "entidadeDescricao"));
                    log.setDetalhes(lerString(rs, "detalhes"));
                    return log;
                });
                if (resultado != null && !resultado.isEmpty()) {
                    return resultado;
                }
            } catch (Exception ignored) {}
        }
        return new ArrayList<>();
    }

    // ==========================================
    // BACKUP DE PESSOAS E HISTÓRICO
    // ==========================================

    public List<Pessoa> listarPessoasPorAnoBackup(int ano) {
        String nomeTabela = "pessoas_" + ano;
        String sql = "SELECT * FROM " + nomeTabela + " ORDER BY nome ASC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapearPessoa(rs));
        } catch (Exception e) {
            System.err.println("Erro ao listar pessoas do backup para o ano " + ano + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Pessoa buscarPessoaPorIdNoBackup(UUID pessoaId, int ano) {
        String nomeTabela = "pessoas_" + ano;
        String sql = "SELECT * FROM " + nomeTabela +
                " WHERE id = ? OR id = UNHEX(REPLACE(?, '-', '')) LIMIT 1";
        try {
            String idStr = pessoaId.toString();
            List<Pessoa> lista = jdbcTemplate.query(sql, (rs, rowNum) -> mapearPessoa(rs), idStr, idStr);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar pessoa no backup " + ano + ": " + e.getMessage());
        }

        Pessoa pessoaFallback = new Pessoa();
        pessoaFallback.setId(pessoaId);
        pessoaFallback.setNome("Pessoa Arquivada");
        return pessoaFallback;
    }

    private Pessoa mapearPessoa(ResultSet rs) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(lerUuid(rs, "id", "ID"));

        String cpf = lerString(rs, "cpf");
        pessoa.setCpf(cpf != null ? cpf : lerString(rs, "documento"));
        pessoa.setIdentidade(lerString(rs, "identidade"));
        pessoa.setNome(lerString(rs, "nome"));
        pessoa.setTelefone(lerString(rs, "telefone"));
        pessoa.setUnidade(lerUnidade(rs));
        pessoa.setSetor(lerString(rs, "setor"));
        pessoa.setAndar(lerString(rs, "andar"));
        pessoa.setPaciente(lerString(rs, "paciente"));
        pessoa.setObservacao(lerString(rs, "observacao"));
        return pessoa;
    }

    public List<Registro> listarRegistrosPorPessoaEAnodeBackup(UUID pessoaId, int ano) {
        String nomeTabela = "registros_" + ano;
        String sql = "SELECT r.*, ue.nome_completo AS nome_entrada, us.nome_completo AS nome_saida " +
                "FROM " + nomeTabela + " r " +
                "LEFT JOIN usuarios ue ON r.usuario_entrada_id = ue.id " +
                "LEFT JOIN usuarios us ON r.usuario_saida_id = us.id " +
                "WHERE r.pessoa_id = ? OR r.pessoa_id = UNHEX(REPLACE(?, '-', '')) " +
                "ORDER BY r.hora_entrada DESC";

        try {
            String idStr = pessoaId.toString();
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Registro registro = new Registro();

                registro.setId(lerUuid(rs, "id"));
                registro.setHoraEntrada(lerDataHora(rs, "hora_entrada", "horaEntrada"));
                registro.setHoraSaida(lerDataHora(rs, "hora_saida", "horaSaida"));
                registro.setUnidade(lerUnidade(rs));
                registro.setSetor(lerString(rs, "setor"));
                registro.setAndar(lerString(rs, "andar"));
                registro.setPaciente(lerString(rs, "paciente"));

                String nomeEntrada = lerString(rs, "nome_entrada");
                if (nomeEntrada != null && !nomeEntrada.isBlank()) {
                    Usuario opEntrada = new Usuario();
                    opEntrada.setNomeCompleto(nomeEntrada);
                    registro.setUsuarioEntrada(opEntrada);
                }

                String nomeSaida = lerString(rs, "nome_saida");
                if (nomeSaida != null && !nomeSaida.isBlank()) {
                    Usuario opSaida = new Usuario();
                    opSaida.setNomeCompleto(nomeSaida);
                    registro.setUsuarioSaida(opSaida);
                }

                return registro;
            }, idStr, idStr);
        } catch (Exception e) {
            System.err.println("Erro ao listar registros de backup para o ano " + ano + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ==========================================
    // BACKUP DE INTERNAÇÃO (PACIENTES E ACOMPANHANTES)
    // ==========================================

    public List<PacienteInternado> listarPacientesInternadosPorAnoBackup(int ano) {
        String nomeTabela = "pacientes_internados_" + ano;
        String sql = "SELECT * FROM " + nomeTabela + " ORDER BY nome ASC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                PacienteInternado pac = new PacienteInternado();
                pac.setId(lerUuid(rs, "id", "ID"));
                pac.setCpf(lerString(rs, "cpf"));
                pac.setNome(lerString(rs, "nome"));
                pac.setTelefone(lerString(rs, "telefone"));
                pac.setUnidade(lerUnidade(rs));
                pac.setAndar(lerString(rs, "andar"));
                pac.setLeito(lerString(rs, "leito"));
                pac.setObservacao(lerString(rs, "observacao"));
                pac.setHoraEntrada(lerDataHora(rs, "hora_entrada", "horaEntrada"));
                pac.setHoraSaida(lerDataHora(rs, "hora_saida", "horaSaida"));

                String statusStr = lerString(rs, "status");
                if (statusStr != null && !statusStr.isBlank()) {
                    try {
                        pac.setStatus(StatusInternacao.valueOf(statusStr.trim()));
                    } catch (Exception ignored) {
                    }
                }
                return pac;
            });
        } catch (Exception e) {
            System.err.println("Erro ao listar pacientes internados do backup para o ano " + ano + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Acompanhante> listarAcompanhantesPorAnoBackup(int ano) {
        String tAcomp = "acompanhantes_" + ano;
        String tPac = "pacientes_internados_" + ano;

        String sql = "SELECT a.*, p.nome AS paciente_nome " +
                "FROM " + tAcomp + " a " +
                "LEFT JOIN " + tPac + " p ON a.paciente_id = p.id " +
                "ORDER BY a.hora_entrada DESC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Acompanhante acomp = new Acompanhante();
                acomp.setId(lerUuid(rs, "id", "ID"));
                acomp.setCpf(lerString(rs, "cpf"));
                acomp.setNome(lerString(rs, "nome"));
                acomp.setTelefone(lerString(rs, "telefone"));
                acomp.setUnidade(lerUnidade(rs));
                acomp.setAndar(lerString(rs, "andar"));
                acomp.setLeito(lerString(rs, "leito"));
                acomp.setHoraEntrada(lerDataHora(rs, "hora_entrada", "horaEntrada"));
                acomp.setHoraSaida(lerDataHora(rs, "hora_saida", "horaSaida"));

                String pacNome = lerString(rs, "paciente_nome");
                if (pacNome != null && !pacNome.isBlank()) {
                    PacienteInternado pac = new PacienteInternado();
                    pac.setNome(pacNome);
                    acomp.setPaciente(pac);
                }
                return acomp;
            });
        } catch (Exception e) {
            System.err.println("Erro ao listar acompanhantes do backup para o ano " + ano + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}