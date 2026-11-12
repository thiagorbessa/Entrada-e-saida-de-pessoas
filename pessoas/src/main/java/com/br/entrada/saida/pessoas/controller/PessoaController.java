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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
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

	private static final java.util.Set<String> CAMPOS_ORDENACAO_PERMITIDOS = java.util.Set.of(
			"cpf", "nome", "telefone", "unidade", "setor", "andar", "paciente", "observacao", "horaEntrada", "horaSaida"
	);

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

	private Pageable ajustarPageableComOrdenacao(Pageable pageable, String sort, String direction) {
		Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
		String campoSeguro = (sort != null && CAMPOS_ORDENACAO_PERMITIDOS.contains(sort)) ? sort : "nome";

		if ("horaEntrada".equals(campoSeguro)) {
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
					JpaSort.unsafe(dir, "r.horaEntrada"));
		}
		if ("horaSaida".equals(campoSeguro)) {
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
					JpaSort.unsafe(dir, "r.horaSaida"));
		}

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(dir, campoSeguro));
	}

	@GetMapping
	public String listar(
			@RequestParam(value = "termo", required = false) String termo,
			@RequestParam(value = "sort", defaultValue = "nome") String sort,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			@PageableDefault(page = 0, size = 10) Pageable pageable,
			Model model,
			Principal principal) {

		Usuario usuario = obterUsuarioLogado(principal);
		Pageable pageableComOrdenacao = ajustarPageableComOrdenacao(pageable, sort, direction);

		Page<Pessoa> paginaPessoas = pessoaService.buscarPorTermoEUsuarioPaginado(termo, usuario, pageableComOrdenacao);

		Map<UUID, Registro> ultimosRegistros = new HashMap<>();
		for (Pessoa p : paginaPessoas.getContent()) {
			Registro ultimo = registroService.buscarUltimoRegistroDaPessoa(p.getId());
			if (ultimo != null) {
				ultimosRegistros.put(p.getId(), ultimo);
			}
		}

		int startElement = paginaPessoas.getTotalElements() == 0 ? 0 : (pageableComOrdenacao.getPageNumber() * pageableComOrdenacao.getPageSize()) + 1;
		long endElement = Math.min((long) (pageableComOrdenacao.getPageNumber() + 1) * pageableComOrdenacao.getPageSize(), paginaPessoas.getTotalElements());

		model.addAttribute("paginaPessoas", paginaPessoas);
		model.addAttribute("pessoas", paginaPessoas.getContent());
		model.addAttribute("ultimosRegistros", ultimosRegistros);
		model.addAttribute("currentPage", pageableComOrdenacao.getPageNumber());
		model.addAttribute("pageSize", pageableComOrdenacao.getPageSize());
		model.addAttribute("totalPages", paginaPessoas.getTotalPages());
		model.addAttribute("totalElements", paginaPessoas.getTotalElements());
		model.addAttribute("startElement", startElement);
		model.addAttribute("endElement", endElement);
		model.addAttribute("termo", termo);
		model.addAttribute("sortField", sort);
		model.addAttribute("sortDir", direction);
		model.addAttribute("usuarioLogado", usuario);

		return "pessoas/listar";
	}

	@GetMapping("/buscar")
	public String buscarParcial(
			@RequestParam(value = "termo", required = false) String termo,
			@RequestParam(value = "sort", defaultValue = "nome") String sort,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			@PageableDefault(page = 0, size = 30) Pageable pageable,
			Model model,
			Principal principal) {

		Usuario usuario = obterUsuarioLogado(principal);
		Pageable pageableComOrdenacao = ajustarPageableComOrdenacao(pageable, sort, direction);

		Page<Pessoa> paginaPessoas = pessoaService.buscarPorTermoEUsuarioPaginado(termo, usuario, pageableComOrdenacao);

		Map<UUID, Registro> ultimosRegistros = new HashMap<>();
		for (Pessoa p : paginaPessoas.getContent()) {
			Registro ultimo = registroService.buscarUltimoRegistroDaPessoa(p.getId());
			if (ultimo != null) {
				ultimosRegistros.put(p.getId(), ultimo);
			}
		}

		model.addAttribute("paginaPessoas", paginaPessoas);
		model.addAttribute("pessoas", paginaPessoas.getContent());
		model.addAttribute("ultimosRegistros", ultimosRegistros);
		model.addAttribute("sortField", sort);
		model.addAttribute("sortDir", direction);

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

		if (!pessoaService.isAdmin(usuarioLogado)) {
			if (pessoa.getUnidade() != null &&
					(usuarioLogado == null || usuarioLogado.getUnidades() == null || !usuarioLogado.getUnidades().contains(pessoa.getUnidade()))) {
				redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar cadastros desta unidade.");
				return "redirect:/pessoas";
			}
		}

		Registro ultimoRegistro = registroService.buscarUltimoRegistroDaPessoa(id);
		model.addAttribute("ultimoRegistro", ultimoRegistro);

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

	@GetMapping("/{id}/historico")
	public String historico(
			@PathVariable UUID id,
			@PageableDefault(page = 0, size = 100, sort = "horaEntrada", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {

		Pessoa pessoa = pessoaService.buscarPorId(id);
		Page<Registro> paginaRegistros = registroService.buscarHistoricoPorPessoaPaginado(id, pageable);

		model.addAttribute("pessoa", pessoa);
		model.addAttribute("registros", paginaRegistros.getContent());
		model.addAttribute("paginaRegistros", paginaRegistros);
		model.addAttribute("currentPage", pageable.getPageNumber());
		model.addAttribute("totalPages", paginaRegistros.getTotalPages());

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