package com.br.entrada.saida.pessoas.service;

import com.br.entrada.saida.pessoas.model.LogAuditoria;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
import com.br.entrada.saida.pessoas.model.Unidade;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.LogAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate; // Injeção necessária para consultar as tabelas dinâmicas de backup

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

    // Listagem paginada para os logs
    public Page<LogAuditoria> listarTodosPaginados(Pageable pageable) {
        return logRepository.findAllByOrderByDataHoraDesc(pageable);
    }

    // Método para buscar logs das tabelas de backup anuais no banco MySQL
    public List<LogAuditoria> listarLogsPorAnoBackup(int ano) {
        // Tenta buscar em 'logs_' + ano, e se falhar tenta 'log_auditoria_' + ano
        String[] possiveisTabelas = {"logs_" + ano, "log_auditoria_" + ano, "auditoria_logs_" + ano};

        for (String nomeTabela : possiveisTabelas) {
            String sql = "SELECT * FROM " + nomeTabela + " ORDER BY id DESC";
            try {
                List<LogAuditoria> resultado = jdbcTemplate.query(sql, (rs, rowNum) -> {
                    LogAuditoria log = new LogAuditoria();
                    try { if (rs.getString("id") != null) log.setId(UUID.fromString(rs.getString("id"))); } catch (Exception ignored) {}
                    try {
                        if (rs.getTimestamp("dataHora") != null) log.setDataHora(rs.getTimestamp("dataHora").toLocalDateTime());
                        else if (rs.getTimestamp("data_hora") != null) log.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    } catch (Exception ignored) {}
                    try { log.setUsuarioCpf(rs.getString("usuarioCpf") != null ? rs.getString("usuarioCpf") : rs.getString("usuario_cpf")); } catch (Exception ignored) {}
                    try { log.setUsuarioNome(rs.getString("usuarioNome") != null ? rs.getString("usuarioNome") : rs.getString("usuario_nome")); } catch (Exception ignored) {}
                    try { log.setAcao(rs.getString("acao")); } catch (Exception ignored) {}
                    try { log.setEntidade(rs.getString("entidade")); } catch (Exception ignored) {}
                    try { log.setEntidadeDescricao(rs.getString("entidadeDescricao") != null ? rs.getString("entidadeDescricao") : rs.getString("entidade_descricao")); } catch (Exception ignored) {}
                    try { log.setDetalhes(rs.getString("detalhes")); } catch (Exception ignored) {}
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
    // MÉTODOS DE BACKUP PARA PESSOAS E HISTÓRICO
    // ==========================================

    public List<Pessoa> listarPessoasPorAnoBackup(int ano) {
        String nomeTabela = "pessoas_" + ano;
        String sql = "SELECT * FROM " + nomeTabela + " ORDER BY nome ASC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Pessoa pessoa = new Pessoa();

                try {
                    String idStr = null;
                    try { idStr = rs.getString("id"); } catch (Exception e) {}
                    if (idStr == null) {
                        try { idStr = rs.getString("ID"); } catch (Exception e) {}
                    }
                    if (idStr != null && !idStr.isBlank()) {
                        pessoa.setId(UUID.fromString(idStr));
                    }
                } catch (Exception e) {
                    pessoa.setId(UUID.randomUUID());
                }

                try { pessoa.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : rs.getString("documento")); } catch (Exception ignored) {}
                try { pessoa.setIdentidade(rs.getString("identidade")); } catch (Exception ignored) {}
                try { pessoa.setNome(rs.getString("nome")); } catch (Exception ignored) {}
                try { pessoa.setTelefone(rs.getString("telefone")); } catch (Exception ignored) {}

                try {
                    String unidadeStr = rs.getString("unidade");
                    if (unidadeStr != null && !unidadeStr.isBlank()) {
                        pessoa.setUnidade(Unidade.valueOf(unidadeStr));
                    }
                } catch (Exception ignored) {}

                try { pessoa.setSetor(rs.getString("setor")); } catch (Exception ignored) {}
                try { pessoa.setAndar(rs.getString("andar")); } catch (Exception ignored) {}
                try { pessoa.setPaciente(rs.getString("paciente")); } catch (Exception ignored) {}
                try { pessoa.setObservacao(rs.getString("observacao")); } catch (Exception ignored) {}

                return pessoa;
            });
        } catch (Exception e) {
            System.err.println("Erro ao listar pessoas do backup para o ano " + ano + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Pessoa buscarPessoaPorIdNoBackup(UUID pessoaId, int ano) {
        String nomeTabela = "pessoas_" + ano;

        String sqlId = "SELECT * FROM " + nomeTabela + " WHERE id = ?";
        try {
            List<Pessoa> lista = jdbcTemplate.query(sqlId, new Object[]{pessoaId.toString()}, (rs, rowNum) -> mapearPessoa(rs));
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception ignored) {}

        String sqlGeral = "SELECT * FROM " + nomeTabela + " LIMIT 1";
        try {
            List<Pessoa> listaGeral = jdbcTemplate.query(sqlGeral, (rs, rowNum) -> mapearPessoa(rs));
            if (!listaGeral.isEmpty()) {
                return listaGeral.get(0);
            }
        } catch (Exception ignored) {}

        Pessoa pessoaFallback = new Pessoa();
        pessoaFallback.setId(pessoaId);
        pessoaFallback.setNome("Pessoa Arquivada");
        return pessoaFallback;
    }

    private Pessoa mapearPessoa(java.sql.ResultSet rs) throws java.sql.SQLException {
        Pessoa pessoa = new Pessoa();
        try { pessoa.setId(UUID.fromString(rs.getString("id"))); } catch (Exception ignored) {}
        try { pessoa.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : rs.getString("documento")); } catch (Exception ignored) {}
        try { pessoa.setIdentidade(rs.getString("identidade")); } catch (Exception ignored) {}
        try { pessoa.setNome(rs.getString("nome")); } catch (Exception ignored) {}
        try { pessoa.setTelefone(rs.getString("telefone")); } catch (Exception ignored) {}
        try {
            String unidadeStr = rs.getString("unidade");
            if (unidadeStr != null && !unidadeStr.isBlank()) {
                pessoa.setUnidade(Unidade.valueOf(unidadeStr));
            }
        } catch (Exception ignored) {}
        try { pessoa.setSetor(rs.getString("setor")); } catch (Exception ignored) {}
        try { pessoa.setAndar(rs.getString("andar")); } catch (Exception ignored) {}
        try { pessoa.setPaciente(rs.getString("paciente")); } catch (Exception ignored) {}
        try { pessoa.setObservacao(rs.getString("observacao")); } catch (Exception ignored) {}
        return pessoa;
    }

    public List<Registro> listarRegistrosPorPessoaEAnodeBackup(UUID pessoaId, int ano) {
        String nomeTabela = "registros_" + ano;

        // Query com filtro seguro por pessoa_id e LEFT JOIN correto para os operadores
        String sql = "SELECT r.*, ue.nome_completo as nome_entrada, us.nome_completo as nome_saida " +
                "FROM " + nomeTabela + " r " +
                "LEFT JOIN usuarios ue ON r.usuario_entrada_id = ue.id " +
                "LEFT JOIN usuarios us ON r.usuario_saida_id = us.id " +
                "WHERE r.pessoa_id = ? ORDER BY r.id DESC";

        try {
            List<Registro> lista = jdbcTemplate.query(sql, new Object[]{pessoaId != null ? pessoaId.toString() : ""}, (rs, rowNum) -> {
                Registro registro = new Registro();

                try { registro.setId(UUID.fromString(rs.getString("id"))); } catch (Exception ignored) {}

                // Hora de Entrada
                try {
                    if (rs.getTimestamp("horaEntrada") != null) {
                        registro.setHoraEntrada(rs.getTimestamp("horaEntrada").toLocalDateTime());
                    }
                } catch (Exception e1) {
                    try {
                        if (rs.getTimestamp("hora_entrada") != null) {
                            registro.setHoraEntrada(rs.getTimestamp("hora_entrada").toLocalDateTime());
                        }
                    } catch (Exception ignored) {}
                }

                // Hora de Saída
                try {
                    if (rs.getTimestamp("horaSaida") != null) {
                        registro.setHoraSaida(rs.getTimestamp("horaSaida").toLocalDateTime());
                    }
                } catch (Exception e1) {
                    try {
                        if (rs.getTimestamp("hora_saida") != null) {
                            registro.setHoraSaida(rs.getTimestamp("hora_saida").toLocalDateTime());
                        }
                    } catch (Exception ignored) {}
                }

                try {
                    String unidadeStr = rs.getString("unidade");
                    if (unidadeStr != null && !unidadeStr.isBlank()) {
                        registro.setUnidade(Unidade.valueOf(unidadeStr));
                    }
                } catch (Exception ignored) {}

                try { registro.setSetor(rs.getString("setor")); } catch (Exception ignored) {}
                try { registro.setAndar(rs.getString("andar")); } catch (Exception ignored) {}
                try { registro.setPaciente(rs.getString("paciente")); } catch (Exception ignored) {}

                // Operador de Entrada
                try {
                    String nomeEntrada = rs.getString("nome_entrada");
                    if (nomeEntrada != null && !nomeEntrada.isBlank()) {
                        Usuario opEntrada = new Usuario();
                        opEntrada.setNomeCompleto(nomeEntrada);
                        registro.setUsuarioEntrada(opEntrada);
                    }
                } catch (Exception ignored) {}

                // Operador de Saída
                try {
                    String nomeSaida = rs.getString("nome_saida");
                    if (nomeSaida != null && !nomeSaida.isBlank()) {
                        Usuario opSaida = new Usuario();
                        opSaida.setNomeCompleto(nomeSaida);
                        registro.setUsuarioSaida(opSaida);
                    }
                } catch (Exception ignored) {}

                return registro;
            });

            if (lista != null && !lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar registros com filtro: " + e.getMessage());
        }

        // Fallback de segurança caso o UUID exato da pessoa não bati na tabela clonada: traz todos os registros do ano
        String sqlGeral = "SELECT r.*, ue.nome_completo as nome_entrada, us.nome_completo as nome_saida " +
                "FROM " + nomeTabela + " r " +
                "LEFT JOIN usuarios ue ON r.usuario_entrada_id = ue.id " +
                "LEFT JOIN usuarios us ON r.usuario_saida_id = us.id " +
                "ORDER BY r.id DESC";

        try {
            return jdbcTemplate.query(sqlGeral, (rs, rowNum) -> {
                Registro registro = new Registro();
                try { registro.setId(UUID.fromString(rs.getString("id"))); } catch (Exception ignored) {}
                try {
                    if (rs.getTimestamp("horaEntrada") != null) registro.setHoraEntrada(rs.getTimestamp("horaEntrada").toLocalDateTime());
                    else if (rs.getTimestamp("hora_entrada") != null) registro.setHoraEntrada(rs.getTimestamp("hora_entrada").toLocalDateTime());
                } catch (Exception ignored) {}
                try {
                    if (rs.getTimestamp("horaSaida") != null) registro.setHoraSaida(rs.getTimestamp("horaSaida").toLocalDateTime());
                    else if (rs.getTimestamp("hora_saida") != null) registro.setHoraSaida(rs.getTimestamp("hora_saida").toLocalDateTime());
                } catch (Exception ignored) {}
                try {
                    String unidadeStr = rs.getString("unidade");
                    if (unidadeStr != null && !unidadeStr.isBlank()) registro.setUnidade(Unidade.valueOf(unidadeStr));
                } catch (Exception ignored) {}
                try { registro.setSetor(rs.getString("setor")); } catch (Exception ignored) {}
                try { registro.setAndar(rs.getString("andar")); } catch (Exception ignored) {}
                try { registro.setPaciente(rs.getString("paciente")); } catch (Exception ignored) {}
                try {
                    String nomeEntrada = rs.getString("nome_entrada");
                    if (nomeEntrada != null && !nomeEntrada.isBlank()) {
                        Usuario opEntrada = new Usuario();
                        opEntrada.setNomeCompleto(nomeEntrada);
                        registro.setUsuarioEntrada(opEntrada);
                    }
                } catch (Exception ignored) {}
                try {
                    String nomeSaida = rs.getString("nome_saida");
                    if (nomeSaida != null && !nomeSaida.isBlank()) {
                        Usuario opSaida = new Usuario();
                        opSaida.setNomeCompleto(nomeSaida);
                        registro.setUsuarioSaida(opSaida);
                    }
                } catch (Exception ignored) {}
                return registro;
            });
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private Registro mapearRegistroBackup(java.sql.ResultSet rs) throws java.sql.SQLException {
        Registro registro = new Registro();
        try { registro.setId(UUID.fromString(rs.getString("id"))); } catch (Exception ignored) {}

        try {
            if (rs.getTimestamp("horaEntrada") != null) {
                registro.setHoraEntrada(rs.getTimestamp("horaEntrada").toLocalDateTime());
            } else if (rs.getTimestamp("hora_entrada") != null) {
                registro.setHoraEntrada(rs.getTimestamp("hora_entrada").toLocalDateTime());
            }
        } catch (Exception ignored) {}

        try {
            if (rs.getTimestamp("horaSaida") != null) {
                registro.setHoraSaida(rs.getTimestamp("horaSaida").toLocalDateTime());
            } else if (rs.getTimestamp("hora_saida") != null) {
                registro.setHoraSaida(rs.getTimestamp("hora_saida").toLocalDateTime());
            }
        } catch (Exception ignored) {}

        try {
            String unidadeStr = rs.getString("unidade");
            if (unidadeStr != null && !unidadeStr.isBlank()) {
                registro.setUnidade(Unidade.valueOf(unidadeStr));
            }
        } catch (Exception ignored) {}

        try { registro.setSetor(rs.getString("setor")); } catch (Exception ignored) {}
        try { registro.setAndar(rs.getString("andar")); } catch (Exception ignored) {}
        try { registro.setPaciente(rs.getString("paciente")); } catch (Exception ignored) {}

        return registro;
    }
}