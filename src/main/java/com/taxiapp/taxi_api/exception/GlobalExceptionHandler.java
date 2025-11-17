package com.taxiapp.taxi_api.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler de Exceções Global para o projeto taxi-api.
 * <p>
 * Anotado com {@code @ControllerAdvice}, esta classe captura exceções
 * lançadas por qualquer Controller da aplicação, centralizando o
 * tratamento de erros e garantindo um formato de resposta JSON padronizado.
 *
 * @see ResourceNotFoundException
 * @see MethodArgumentNotValidException
 * @see IllegalArgumentException
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // --- Métodos de Construção da Resposta (Helpers) ---

    /**
     * Constrói a resposta de erro principal com detalhes de validação opcionais.
     *
     * @param status           O HttpStatus (ex: 404, 400).
     * @param message          A mensagem de erro principal.
     * @param validationErrors Um mapa de erros de validação (pode ser nulo).
     * @return Um ResponseEntity padronizado.
     */
    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, Map<String, String> validationErrors) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", (message == null || message.isBlank()) ? defaultMessage(status) : message);

        if (validationErrors != null && !validationErrors.isEmpty()) {
            body.put("validationErrors", validationErrors);
        }

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Sobrecarga do construtor de resposta para erros sem validação.
     *
     * @param status  O HttpStatus.
     * @param message A mensagem de erro.
     * @return Um ResponseEntity padronizado.
     */
    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return buildErrorResponse(status, message, null);
    }

    /**
     * Retorna uma mensagem padrão caso nenhuma seja fornecida.
     *
     * @param status O HttpStatus.
     * @return A mensagem de erro padrão.
     */
    private String defaultMessage(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> "Recurso não encontrado.";
            case BAD_REQUEST -> "Requisição inválida.";
            case CONFLICT -> "Conflito de dados. A operação não pôde ser concluída.";
            default -> "Ocorreu um erro interno no servidor.";
        };
    }

    // --- Handlers de Exceção Específicos ---

    /**
     * Captura a exceção {@link ResourceNotFoundException} (nosso 404 customizado).
     * <p>
     * Lançada pelo {@code DriverService} quando `findById` falha.
     *
     * @param ex A exceção capturada.
     * @return Um ResponseEntity com status 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Captura {@link IllegalArgumentException} (nossas regras de negócio).
     * <p>
     * Lançada pelo {@code DriverService} ao tentar criar/atualizar um motorista
     * com CNH ou Placa de Veículo que já existem.
     * <p>
     * <strong>Lógica:</strong> Se a mensagem contém "registered" ou "já existe",
     * retorna 409 (Conflict). Caso contrário, retorna 400 (Bad Request).
     *
     * @param ex A exceção capturada.
     * @return Um ResponseEntity com status 409 (Conflict) ou 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        String msg = ex.getMessage();
        String lowerMsg = (msg == null) ? "" : msg.toLowerCase();

        // Se for um erro de duplicidade, é um CONFLITO (409)
        if (lowerMsg.contains("registered") || lowerMsg.contains("já existe")) {
            return buildErrorResponse(HttpStatus.CONFLICT, msg);
        }

        // Outros argumentos ilegais são uma Requisição Inválida (400)
        return buildErrorResponse(HttpStatus.BAD_REQUEST, msg);
    }

    /**
     * Captura {@link MethodArgumentNotValidException} (Validação de DTOs).
     * <p>
     * Acionado quando as anotações (@NotBlank, @Size) no
     * {@code DriverRequestDTO} falham.
     * <p>
     * Retorna um mapa detalhado de quais campos falharam na validação.
     *
     * @param ex A exceção de validação do Spring.
     * @return Um ResponseEntity com status 400 (Bad Request) e os detalhes dos erros.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Um ou mais campos falharam na validação.", errors);
    }

    /**
     * Captura {@link DataIntegrityViolationException} (Erros de Banco de Dados).
     * <p>
     * Acionado pelo Spring quando uma operação no banco viola uma restrição
     * (ex: `unique key` não capturada pela lógica de serviço).
     *
     * @param ex A exceção de integridade de dados.
     * @return Um ResponseEntity com status 409 (Conflict).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {
        String cause = ex.getMostSpecificCause().getMessage();
        String simplifiedMessage = "Violação de integridade de dados.";

        // Tenta extrair uma mensagem mais amigável
        if (cause.contains("violates unique constraint")) {
            simplifiedMessage = "Erro: Já existe um registro com estes dados.";
        } else if (cause.contains("violates foreign key constraint")) {
            simplifiedMessage = "Erro: Este registro está associado a outros dados e não pode ser alterado/excluído.";
        }
        
        return buildErrorResponse(HttpStatus.CONFLICT, simplifiedMessage);
    }

    /**
     * Captura genérica para qualquer outra exceção não tratada.
     * <p>
     * É o "safety net" que garante que o usuário nunca verá um erro
     * de servidor não formatado.
     *
     * @param ex A exceção geral.
     * @return Um ResponseEntity com status 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Logar o erro completo no servidor é uma boa prática aqui
        // ex.printStackTrace();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}