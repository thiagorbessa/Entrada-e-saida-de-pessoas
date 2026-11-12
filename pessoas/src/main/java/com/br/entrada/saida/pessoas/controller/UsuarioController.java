package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.dto.UsuarioRequestDTO;
import com.br.entrada.saida.pessoas.exception.AcessoNegadoException;
import com.br.entrada.saida.pessoas.exception.CpfDuplicadoException;
import com.br.entrada.saida.pessoas.exception.RegraNegocioException;
import com.br.entrada.saida.pessoas.exception.SistemaException;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    private Usuario getUsuarioLogado(Authentication auth) {
        if (auth == null || auth.getName() == null) return null;
        return usuarioRepository.findByCpf(auth.getName()).orElse(null);
    }

    private boolean isAdminGeral(Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GERAL"));
    }

    private void prepararModelFormulario(Model model, Usuario usuarioLogado, boolean isGeral) {
        model.addAttribute("podeEscolherPerfil", isGeral);
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("isGeralLogado", isGeral);
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model.addAttribute("usuario", new UsuarioRequestDTO());
        prepararModelFormulario(model, getUsuarioLogado(auth), isAdminGeral(auth));
        return "usuarios/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuario") UsuarioRequestDTO dto,
                         BindingResult result,
                         Model model,
                         Authentication auth,
                         RedirectAttributes redirectAttributes) {

        Usuario usuarioLogado = getUsuarioLogado(auth);
        boolean isGeral = isAdminGeral(auth);

        if (!isGeral) {
            dto.setPerfil("ROLE_USER");
        }

        if (result.hasErrors()) {
            prepararModelFormulario(model, usuarioLogado, isGeral);
            return "usuarios/formulario";
        }

        try {
            usuarioService.salvar(dto, usuarioLogado);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
        } catch (CpfDuplicadoException e) {
            result.rejectValue("cpf", "error.cpf", e.getMessage());
            prepararModelFormulario(model, usuarioLogado, isGeral);
            return "usuarios/formulario";
        } catch (AcessoNegadoException e) {
            result.rejectValue("nomeCompleto", "error.security", e.getMessage());
            prepararModelFormulario(model, usuarioLogado, isGeral);
            return "usuarios/formulario";
        } catch (RegraNegocioException e) {
            result.rejectValue("nomeCompleto", "error.regra", e.getMessage());
            prepararModelFormulario(model, usuarioLogado, isGeral);
            return "usuarios/formulario";
        } catch (Exception e) {
            result.rejectValue("nomeCompleto", "error.geral", "Erro ao salvar usuário: " + e.getMessage());
            prepararModelFormulario(model, usuarioLogado, isGeral);
            return "usuarios/formulario";
        }

        return "redirect:/usuarios";
    }

    @GetMapping
    public String listar(Model model, Authentication auth) {
        boolean isGeral = isAdminGeral(auth);

        List<Usuario> usuarios;
        if (isGeral) {
            usuarios = usuarioRepository.findAll();
        } else {
            usuarios = usuarioRepository.findAll().stream()
                    .filter(u -> "ROLE_USER".equals(u.getPerfil()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("isGeral", isGeral);
        return "usuarios/listar-usuarios";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = getUsuarioLogado(auth);
        usuarioService.deletar(id, usuarioLogado);
        redirectAttributes.addFlashAttribute("sucesso", "Usuário excluído com sucesso.");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, Authentication auth) {
        Usuario usuario = usuarioService.buscarPorId(id);
        Usuario usuarioLogado = getUsuarioLogado(auth);
        boolean isGeral = isAdminGeral(auth);

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setId(usuario.getId());
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setCpf(usuario.getCpf());
        dto.setPerfil(usuario.getPerfil());
        dto.setUnidades(usuario.getUnidades());

        model.addAttribute("usuario", dto);
        prepararModelFormulario(model, usuarioLogado, isGeral);
        return "usuarios/formulario";
    }

    @PostMapping("/alterar-senha-propria")
    public String alterarSenhaPropria(@RequestParam("senhaAntiga") String senhaAntiga,
                                      @RequestParam("senhaNova") String senhaNova,
                                      Authentication auth,
                                      RedirectAttributes redirectAttributes) {
        try {
            Usuario usuarioLogado = getUsuarioLogado(auth);
            usuarioService.alterarSenha(usuarioLogado, senhaAntiga, senhaNova);
            redirectAttributes.addFlashAttribute("sucesso", "Senha alterada com sucesso!");
        } catch (SistemaException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível alterar a senha. Tente novamente.");
        }
        return "redirect:/";
    }
}