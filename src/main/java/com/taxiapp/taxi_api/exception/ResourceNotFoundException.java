package com.taxiapp.taxi_api.exception;

/**
 * Exceção customizada para representar um recurso não encontrado (HTTP 404).
 * <p>
 * Esta exceção é lançada pela camada de serviço (ex: DriverService)
 * quando uma busca por ID não retorna resultados.
 * <p>
 * Ela será capturada pelo {@link GlobalExceptionHandler} para
 * gerar uma resposta HTTP 404 padronizada.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     * @param message A mensagem detalhando o erro (ex: "Driver not found with id: 5").
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}