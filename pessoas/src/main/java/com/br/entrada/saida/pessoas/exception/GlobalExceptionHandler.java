package com.br.entrada.saida.pessoas.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URI;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private boolean isRequisicaoGet(HttpServletRequest request) {
        return "GET".equalsIgnoreCase(request.getMethod());
    }

    private boolean isRequisicaoParcial(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(requestedWith) || (uri != null && uri.endsWith("/buscar"));
    }

    private String resolverDestinoRedirect(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String currentUri = request.getRequestURI();

        if (referer != null && !referer.isBlank()) {
            try {
                URI uri = URI.create(referer);
                String path = uri.getPath();
                if (path != null && !path.isBlank() && !path.equals(currentUri)) {
                    String query = uri.getQuery();
                    return "redirect:" + path + (query != null ? "?" + query : "");
                }
            } catch (IllegalArgumentException ignored) {
                // Referer malformado: usa fallback seguro
            }
        }
        return "redirect:/pessoas";
    }

    private String responderErro(HttpStatus status,
                                 String titulo,
                                 String mensagemUsuario,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {
        response.setStatus(status.value());

        if (isRequisicaoParcial(request)) {
            model.addAttribute("erro", mensagemUsuario);
            return "error :: mensagemParcial";
        }

        // Em requisições GET, renderizar a página de erro evita loops de redirecionamento
        if (isRequisicaoGet(request)) {
            model.addAttribute("status", status.value());
            model.addAttribute("titulo", titulo);
            model.addAttribute("mensagem", mensagemUsuario);
            return "error";
        }

        redirectAttributes.addFlashAttribute("erro", mensagemUsuario);
        return resolverDestinoRedirect(request);
    }

    @ExceptionHandler(SistemaException.class)
    public String handleSistemaException(SistemaException ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model) {
        log.warn("Regra de negócio/domínio [{}]: {}", ex.getTitulo(), ex.getMessage());
        return responderErro(ex.getStatus(), ex.getTitulo(), ex.getMessage(),
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler({AccessDeniedException.class, SecurityException.class})
    public String handleAccessDenied(Exception ex,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model) {
        log.warn("Tentativa de acesso não autorizado em {}: {}", request.getRequestURI(), ex.getMessage());
        String msg = (ex instanceof SecurityException && ex.getMessage() != null)
                ? ex.getMessage()
                : "Você não tem permissão para realizar esta operação.";
        return responderErro(HttpStatus.FORBIDDEN, "Acesso negado", msg,
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex,
                                             RedirectAttributes redirectAttributes,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             Model model) {
        String mensagemErro = ex.getBindingResult().getAllErrors().isEmpty()
                ? "Dados inválidos enviados no formulário."
                : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return responderErro(HttpStatus.BAD_REQUEST, "Dados inválidos", mensagemErro,
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, PropertyReferenceException.class, IllegalArgumentException.class})
    public String handleBadRequest(Exception ex,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Model model) {
        log.warn("Parâmetro inválido na requisição {}: {}", request.getRequestURI(), ex.getMessage());
        String msg = (ex instanceof IllegalArgumentException && ex.getMessage() != null)
                ? ex.getMessage()
                : "Os parâmetros informados na requisição são inválidos.";
        return responderErro(HttpStatus.BAD_REQUEST, "Requisição inválida", msg,
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model) {
        log.warn("Estado inválido em {}: {}", request.getRequestURI(), ex.getMessage());
        return responderErro(HttpStatus.CONFLICT, "Conflito de operação", ex.getMessage(),
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrity(DataIntegrityViolationException ex,
                                      RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      Model model) {
        log.error("Violação de integridade no banco de dados em {}", request.getRequestURI(), ex);
        return responderErro(HttpStatus.CONFLICT, "Conflito de dados",
                "Não foi possível concluir a operação devido a um conflito de dados ou registro vinculado.",
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSize(MaxUploadSizeExceededException ex,
                                      RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      Model model) {
        log.warn("Arquivo excedeu o tamanho máximo permitido: {}", ex.getMessage());
        return responderErro(HttpStatus.PAYLOAD_TOO_LARGE, "Arquivo muito grande",
                "O arquivo enviado excede o tamanho máximo permitido pelo servidor.",
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNotFound(NoResourceFoundException ex,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {
        return responderErro(HttpStatus.NOT_FOUND, "Página não encontrada",
                "O endereço ou recurso solicitado não existe.",
                redirectAttributes, request, response, model);
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model) {
        // Loga o stack trace completo no servidor, mas exibe mensagem segura ao usuário
        log.error("Erro interno inesperado na rota {} [{}]", request.getRequestURI(), request.getMethod(), ex);
        return responderErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do sistema",
                "Ocorreu uma falha inesperada ao processar sua solicitação. Tente novamente ou contate o suporte.",
                redirectAttributes, request, response, model);
    }
}