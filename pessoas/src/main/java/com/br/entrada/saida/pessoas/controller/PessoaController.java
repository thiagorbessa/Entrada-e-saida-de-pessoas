package com.br.entrada.saida.pessoas.controller;

import com.br.entrada.saida.pessoas.dto.PessoaRequestDTO;
import com.br.entrada.saida.pessoas.exception.CpfPessoaDuplicadoException;
import com.br.entrada.saida.pessoas.model.Pessoa;
import com.br.entrada.saida.pessoas.model.Registro;
import com.br.entrada.saida.pessoas.model.Unidade;
import com.br.entrada.saida.pessoas.model.Usuario;
import com.br.entrada.saida.pessoas.repository.UsuarioRepository;
import com.br.entrada.saida.pessoas.service.PessoaService;
import com.br.entrada.saida.pessoas.service.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
=======
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

	private final PessoaService pessoaService;
	private final RegistroService registroService;
	private final UsuarioRepository usuarioRepository;

	private Usuario obterUsuarioLogado(Principal principal) {
		if (principal == null) return null;
		return usuarioRepository.findByCpf(principal.getName()).orElse(null);
	}

	private void prepararModelComUnidades(Model model, Usuario usuarioLogado) {
		List<Unidade> unidadesPermitidas;

		boolean isAdmin = pessoaService.isAdmin(usuarioLogado);

		if (isAdmin) {
			unidadesPermitidas = List.of(Unidade.values());
		} else if (usuarioLogado != null && usuarioLogado.getUnidades() != null && !usuarioLogado.getUnidades().isEmpty()) {
			unidadesPermitidas = usuarioLogado.getUnidades().stream().collect(Collectors.toList());
		} else {
			unidadesPermitidas = List.of();
		}

		model.addAttribute("unidadesPermitidas", unidadesPermitidas);
		model.addAttribute("usuarioLogado", usuarioLogado);
	}

<<<<<<< HEAD
	// Listagem principal configurada para exibir 100 pessoas por página
	@GetMapping
	public String listar(
			@PageableDefault(page = 0, size = 100, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
			Model model, Principal principal) {

		Usuario usuarioLogado = obterUsuarioLogado(principal);
		Page<Pessoa> paginaPessoas = pessoaService.listarPorUsuarioPaginado(usuarioLogado, pageable);

		List<Pessoa> pessoas = paginaPessoas.getContent();
		model.addAttribute("pessoas", pessoas);
		model.addAttribute("paginaPessoas", paginaPessoas);
		model.addAttribute("currentPage", pageable.getPageNumber());
		model.addAttribute("totalPages", paginaPessoas.getTotalPages());
		model.addAttribute("totalItems", paginaPessoas.getTotalElements());
=======
	@GetMapping
	public String listar(Model model, Principal principal) {
		Usuario usuarioLogado = obterUsuarioLogado(principal);
		List<Pessoa> pessoas = pessoaService.listarPorUsuario(usuarioLogado);
		model.addAttribute("pessoas", pessoas);
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70

		Map<UUID, Registro> registrosAbertos = new HashMap<>();
		if (pessoas != null) {
			for (Pessoa p : pessoas) {
				if (p != null && p.getId() != null) {
					registroService.buscarRegistroAbertoPorPessoa(p.getId())
							.ifPresent(reg -> registrosAbertos.put(p.getId(), reg));
				}
			}
		}
		model.addAttribute("registrosAbertos", registrosAbertos);
		model.addAttribute("usuarioLogado", usuarioLogado);
		return "pessoas/listar";
	}

<<<<<<< HEAD
	// Busca que varre toda a base filtrada pelo termo digitado
=======
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
	@GetMapping("/buscar")
	public String buscar(@RequestParam(value = "termo", required = false) String termo, Model model, Principal principal) {
		Usuario usuarioLogado = obterUsuarioLogado(principal);
		List<Pessoa> pessoas = pessoaService.buscarPorTermoEUsuario(termo, usuarioLogado);
		model.addAttribute("pessoas", pessoas);

		Map<UUID, Registro> registrosAbertos = new HashMap<>();
		if (pessoas != null) {
			for (Pessoa p : pessoas) {
				if (p != null && p.getId() != null) {
					registroService.buscarRegistroAbertoPorPessoa(p.getId())
							.ifPresent(reg -> registrosAbertos.put(p.getId(), reg));
				}
			}
		}
		model.addAttribute("registrosAbertos", registrosAbertos);
		model.addAttribute("usuarioLogado", usuarioLogado);
		return "pessoas/listar :: tabela";
	}

	@GetMapping("/novo")
	public String novo(Model model, Principal principal) {
		Usuario usuarioLogado = obterUsuarioLogado(principal);
		model.addAttribute("pessoa", new PessoaRequestDTO());
		prepararModelComUnidades(model, usuarioLogado);
		return "pessoas/formulario";
	}

	@PostMapping
	public String salvar(@Valid @ModelAttribute("pessoa") PessoaRequestDTO pessoaDto,
						 BindingResult result,
						 Model model,
						 Principal principal,
						 RedirectAttributes redirectAttributes) {

		Usuario usuarioLogado = obterUsuarioLogado(principal);

		if (result.hasErrors()) {
			prepararModelComUnidades(model, usuarioLogado);
			return "pessoas/formulario";
		}

		try {
			pessoaService.salvar(pessoaDto, usuarioLogado);
			redirectAttributes.addFlashAttribute("sucesso", "Cadastro salvo com sucesso!");
		} catch (CpfPessoaDuplicadoException e) {
			result.rejectValue("cpf", "error.cpf", e.getMessage());
			prepararModelComUnidades(model, usuarioLogado);
			return "pessoas/formulario";
		} catch (SecurityException e) {
			result.reject("erro", e.getMessage());
			prepararModelComUnidades(model, usuarioLogado);
			return "pessoas/formulario";
		} catch (Exception e) {
			result.reject("erro", "Erro ao salvar pessoa: " + e.getMessage());
			prepararModelComUnidades(model, usuarioLogado);
			return "pessoas/formulario";
		}

		return "redirect:/pessoas";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable UUID id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
		Usuario usuarioLogado = obterUsuarioLogado(principal);
		Pessoa pessoa = pessoaService.buscarPorId(id);

<<<<<<< HEAD
=======
		// Bloqueia a abertura se o usuário comum não tiver permissão na unidade da pessoa
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
		if (!pessoaService.isAdmin(usuarioLogado)) {
			if (pessoa.getUnidade() != null &&
					(usuarioLogado == null || usuarioLogado.getUnidades() == null || !usuarioLogado.getUnidades().contains(pessoa.getUnidade()))) {
				redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar cadastros desta unidade.");
				return "redirect:/pessoas";
			}
		}

		PessoaRequestDTO dto = new PessoaRequestDTO();
		dto.setId(pessoa.getId().toString());
		dto.setCpf(pessoa.getCpf());
		dto.setIdentidade(pessoa.getIdentidade());
		dto.setNome(pessoa.getNome());
		dto.setTelefone(pessoa.getTelefone());
		dto.setUnidade(pessoa.getUnidade());
		dto.setSetor(pessoa.getSetor());
		dto.setAndar(pessoa.getAndar());
		dto.setPaciente(pessoa.getPaciente());
		dto.setObservacao(pessoa.getObservacao());

		model.addAttribute("pessoa", dto);
		prepararModelComUnidades(model, usuarioLogado);
		return "pessoas/formulario";
	}

<<<<<<< HEAD
	// Histórico atualizado com paginação (100 registros por página)
	@GetMapping("/{id}/historico")
	public String historico(
			@PathVariable UUID id,
			@PageableDefault(page = 0, size = 100, sort = "dataHoraEntrada", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {

		Pessoa pessoa = pessoaService.buscarPorId(id);
		Page<Registro> paginaRegistros = registroService.buscarHistoricoPorPessoaPaginado(id, pageable);

		model.addAttribute("pessoa", pessoa);
		model.addAttribute("registros", paginaRegistros.getContent());
		model.addAttribute("paginaRegistros", paginaRegistros);
		model.addAttribute("currentPage", pageable.getPageNumber());
		model.addAttribute("totalPages", paginaRegistros.getTotalPages());

=======
	@GetMapping("/{id}/historico")
	public String historico(@PathVariable UUID id, Model model) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		model.addAttribute("pessoa", pessoa);
		model.addAttribute("registros", pessoa.getRegistros());
>>>>>>> 83f1b7ba020a81546cd0af66f461e3ffad2f4e70
		return "pessoas/historico";
	}

	@DeleteMapping("/{id}")
	public String excluir(@PathVariable UUID id, Principal principal, RedirectAttributes redirectAttributes) {
		Usuario usuarioLogado = obterUsuarioLogado(principal);
		try {
			pessoaService.deletar(id, usuarioLogado);
			redirectAttributes.addFlashAttribute("sucesso", "Registro de pessoa excluído com sucesso.");
		} catch (SecurityException e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir o registro: " + e.getMessage());
		}
		return "redirect:/pessoas";
	}
}