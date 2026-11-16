package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.dto.AcompanhanteRecord;
import com.br.entrada.saida.pessoas.dto.PacienteInternadoRecord;
import com.br.entrada.saida.pessoas.model.Acompanhante;
import com.br.entrada.saida.pessoas.model.PacienteInternado;
import com.br.entrada.saida.pessoas.model.StatusInternacao;
import com.br.entrada.saida.pessoas.model.Unidade;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.AcompanhanteRepository;
import com.br.entrada.saida.pessoas.repository.PacienteInternadoRepository;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.InternacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/internacao")
@RequiredArgsConstructor
public class InternacaoController {

    private final InternacaoService internacaoService;
    private final PacienteInternadoRepository pacienteRepository;
    private final AcompanhanteRepository acompanhanteRepository;
    private final UsuarioRepository usuarioRepository;

    private Usuario obterUsuarioLogado(Principal principal) {
        if (principal == null) return null;
        return usuarioRepository.findByCpf(principal.getName()).orElse(null);
    }

    private List<Unidade> obterUnidadesPermitidas(Usuario usuarioLogado) {
        if (internacaoService.isAdmin(usuarioLogado)) {
            return List.of(Unidade.values());
        }
        if (usuarioLogado != null && usuarioLogado.getUnidades() != null && !usuarioLogado.getUnidades().isEmpty()) {
            return usuarioLogado.getUnidades().stream().collect(Collectors.toList());
        }
        return List.of();
    }

    @GetMapping
    public String listar(Model model, Principal principal) {
        Usuario usuarioLogado = obterUsuarioLogado(principal);

        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("pacientes", internacaoService.listarPacientesPorUsuario(usuarioLogado));
        model.addAttribute("pacientesAtivos", internacaoService.listarPacientesAtivosPorUsuario(usuarioLogado));
        model.addAttribute("acompanhantes", internacaoService.listarAcompanhantesPorUsuario(usuarioLogado));
        model.addAttribute("unidades", obterUnidadesPermitidas(usuarioLogado));

        return "internacao/listar-internacao";
    }

    @PostMapping({"/paciente", "/paciente/salvar"})
    public String salvarPaciente(@Valid @ModelAttribute PacienteInternadoRecord dto,
                                 BindingResult result,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/internacao";
        }

        try {
            internacaoService.registrarEntradaPaciente(dto, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Entrada do paciente internado registrada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/paciente/entrada/{id}")
    public String registrarNovaEntradaPaciente(@PathVariable UUID id,
                                               Principal principal,
                                               RedirectAttributes redirectAttributes) {
        try {
            internacaoService.registrarNovaEntradaPaciente(id, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Nova entrada do paciente registrada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/paciente/saida/{id}")
    public String registrarSaidaPaciente(@PathVariable UUID id,
                                         @RequestParam("statusSaida") StatusInternacao statusSaida,
                                         Principal principal,
                                         RedirectAttributes redirectAttributes) {
        try {
            internacaoService.registrarSaidaPaciente(id, statusSaida, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso",
                    "Saída do paciente registrada como " + statusSaida.getDescricao() + ".");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping({"/acompanhante", "/acompanhante/salvar"})
    public String salvarAcompanhante(@Valid @ModelAttribute AcompanhanteRecord dto,
                                     BindingResult result,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/internacao";
        }

        try {
            internacaoService.registrarEntradaAcompanhante(dto, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Entrada de acompanhante autorizada!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/acompanhante/saida/{id}")
    public String registrarSaidaAcompanhante(@PathVariable UUID id,
                                             Principal principal,
                                             RedirectAttributes redirectAttributes) {
        try {
            internacaoService.registrarSaidaAcompanhante(id, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Saída do acompanhante registrada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/acompanhante/entrada/{id}")
    public String registrarNovaEntradaAcompanhante(@PathVariable UUID id,
                                                   Principal principal,
                                                   RedirectAttributes redirectAttributes) {
        try {
            internacaoService.registrarNovaEntradaAcompanhante(id, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Nova entrada do acompanhante autorizada!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @GetMapping("/paciente/{id}/historico")
    public String historicoPaciente(@PathVariable UUID id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = obterUsuarioLogado(principal);
        PacienteInternado paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente internado não encontrado."));

        try {
            internacaoService.validarPermissaoUnidade(usuarioLogado, paciente.getUnidade());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/internacao";
        }

        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("tipo", "PACIENTE");
        model.addAttribute("pessoa", paciente);
        model.addAttribute("pacienteVinculado", paciente.getNome());
        model.addAttribute("operadorCadastro",
                paciente.getUsuarioSaida() != null ? paciente.getUsuarioSaida().getNomeCompleto()
                        : (paciente.getUsuarioEntrada() != null ? paciente.getUsuarioEntrada().getNomeCompleto() : "-"));
        model.addAttribute("logs", internacaoService.buscarLogsPaciente(id));

        return "internacao/historico-internacao";
    }

    @GetMapping("/acompanhante/{id}/historico")
    public String historicoAcompanhante(@PathVariable UUID id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = obterUsuarioLogado(principal);
        Acompanhante acompanhante = acompanhanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acompanhante não encontrado."));

        try {
            internacaoService.validarPermissaoUnidade(usuarioLogado, acompanhante.getUnidade());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/internacao";
        }

        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("tipo", "ACOMPANHANTE");
        model.addAttribute("pessoa", acompanhante);
        model.addAttribute("pacienteVinculado",
                acompanhante.getPaciente() != null ? acompanhante.getPaciente().getNome() : "-");
        model.addAttribute("operadorCadastro",
                acompanhante.getUsuarioSaida() != null ? acompanhante.getUsuarioSaida().getNomeCompleto()
                        : (acompanhante.getUsuarioEntrada() != null ? acompanhante.getUsuarioEntrada().getNomeCompleto() : "-"));
        model.addAttribute("logs", internacaoService.buscarLogsAcompanhante(id));

        return "internacao/historico-internacao";
    }

    @PostMapping("/paciente/editar/{id}")
    public String editarPaciente(@PathVariable UUID id,
                                 @Valid @ModelAttribute PacienteInternadoRecord dto,
                                 BindingResult result,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/internacao";
        }
        try {
            internacaoService.editarPaciente(id, dto, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Dados do paciente atualizados com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/paciente/deletar/{id}")
    public String excluirPaciente(@PathVariable UUID id,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        try {
            internacaoService.excluirPaciente(id, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Registro de paciente excluído com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/acompanhante/editar/{id}")
    public String editarAcompanhante(@PathVariable UUID id,
                                     @Valid @ModelAttribute AcompanhanteRecord dto,
                                     BindingResult result,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/internacao";
        }
        try {
            internacaoService.editarAcompanhante(id, dto, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Dados do acompanhante atualizados com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }

    @PostMapping("/acompanhante/deletar/{id}")
    public String excluirAcompanhante(@PathVariable UUID id,
                                      Principal principal,
                                      RedirectAttributes redirectAttributes) {
        try {
            internacaoService.excluirAcompanhante(id, obterUsuarioLogado(principal));
            redirectAttributes.addFlashAttribute("sucesso", "Registro de acompanhante excluído com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/internacao";
    }
}