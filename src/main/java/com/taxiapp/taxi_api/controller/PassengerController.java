package com.taxiapp.taxi_api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.service.PassengerService;

import jakarta.validation.Valid;

/**
 * Controlador REST API para gerenciar a entidade {@link Passenger}.
 *
 * <p>Expõe endpoints CRUD (Create, Read, Update, Delete)
 * para operações com passageiros.</p>
 *
 * <p>@RestController combina @Controller e @ResponseBody,
 * significando que os retornos dos métodos serão serializados (ex: para JSON).</p>
 * <p>@RequestMapping define a URL base "/api/passengers" para todos os
 * endpoints definidos nesta classe.</p>
 *
 * @see PassengerService
 * @see PassengerRequestDTO
 * @see PassengerResponseDTO
 * @author albiecr
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    /**
     * Injeção da camada de serviço (Service) que contém as regras de
     * negócio para operações com passageiros.
     */
    @Autowired
    private PassengerService passengerService;

    /**
     * <h3>Endpoint: CREATE (Criar)</h3>
     * Cria um novo passageiro no sistema.
     *
     * <p>Mapeado para: <code>POST /api/passengers</code></p>
     *
     * <p>A anotação {@link Valid} ativa as validações
     * (ex: @NotBlank, @Email) definidas no DTO.</p>
     *
     * @param requestDTO O DTO (JSON) contendo os dados do novo
     * passageiro a ser criado.
     * @return Um {@link ResponseEntity} com status <strong>201 Created</strong>,
     * o {@link PassengerResponseDTO} do passageiro criado no corpo
     * e um cabeçalho "Location" com a URL para o novo recurso.
     * @see PassengerService#createPassenger(PassengerRequestDTO)
     */
    @PostMapping
    public ResponseEntity<PassengerResponseDTO> createPAssenger(
            @Valid @RequestBody PassengerRequestDTO requestDTO) {
        // 1. Chama o service para criar o passageiro
        PassengerResponseDTO response = passengerService.createPassenger(requestDTO);

        // 2. Retorna uma resposta "201 Created" (boa prática de API)
        // Isso inclui um cabeçalho "Location" com a URL do novo recurso
        return ResponseEntity.created(URI.create("/api/passengers/" + response.id())).body(response);
    }

    /**
     * <h3>Endpoint: READ (Ler por ID)</h3>
     * Busca um passageiro específico pelo seu ID.
     *
     * <p>Mapeado para: <code>GET /api/passengers/{id}</code>
     * (ex: /api/passengers/1)</p>
     *
     * @param id O ID (Long) do passageiro a ser buscado,
     * fornecido como uma variável de caminho (path variable).
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e o {@link PassengerResponseDTO} no corpo, caso encontrado.
     * <p>Retorna <strong>404 Not Found</strong> se o ID não existir.</p>
     * @see PassengerService#getPassengerbyId(Long)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> getPassengerById(@PathVariable Long id) {
        // 1. Chama o service para obter o passageiro pelo ID
        Optional<PassengerResponseDTO> passenger = passengerService.getPassengerbyId(id);

        // 2. Retorna 200 OK (se encontrou) ou 404 Not Found (se não encontrou)
        return passenger.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * <h3>Endpoint: READ (Ler Todos)</h3>
     * Retorna uma lista com todos os passageiros cadastrados no sistema.
     *
     * <p>Mapeado para: <code>GET /api/passengers</code></p>
     *
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e uma {@link List} de {@link PassengerResponseDTO} no corpo.
     * <p>A lista pode estar vazia se não houver passageiros.</p>
     * @see PassengerService#getAllPassengers()
     */
    @GetMapping
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers() {
        // 1. Chama o service para obter todos os passageiros
        List<PassengerResponseDTO> passengers = passengerService.getAllPassengers();
        // 2. Retorna 200 OK com a lista (pode ser vazia)
        return ResponseEntity.ok(passengers);
    }

    /**
     * <h3>Endpoint: UPDATE (Atualizar)</h3>
     * Atualiza os dados de um passageiro existente.
     *
     * <p>Mapeado para: <code>PUT /api/passengers/{id}</code></p>
     *
     * @param id O ID (Long) do passageiro a ser atualizado (da URL).
     * @param requestDTO O DTO (JSON) com os novos dados para o passageiro.
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e o {@link PassengerResponseDTO} com os dados atualizados.
     * <p>Retorna <strong>404 Not Found</strong> se o ID do
     * passageiro não for encontrado.</p>
     * @see PassengerService#updatePassenger(Long, PassengerRequestDTO)
     */
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable Long id,
            @Valid @RequestBody PassengerRequestDTO requestDTO) {
        try {
            // Chama o service para atualizar o passageiro
            PassengerResponseDTO updatedPassenger = passengerService.updatePassenger(id, requestDTO);

            // Retorna 200 OK (se atualizou)
            return ResponseEntity.ok(updatedPassenger);
        } catch (IllegalArgumentException e) {
            // Se o service lançou exceção (ex: "Passenger not found"), retorna 404
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * <h3>Endpoint: DELETE (Deletar)</h3>
     * Remove um passageiro do sistema.
     *
     * <p>Mapeado para: <code>DELETE /api/passengers/{id}</code></p>
     *
     * @param id O ID (Long) do passageiro a ser deletado (da URL).
     * @return Um {@link ResponseEntity} com status <strong>204 No Content</strong>
     * se a deleção for bem-sucedida.
     * <p>Retorna <strong>404 Not Found</strong> se o ID do
     * passageiro não for encontrado.</p>
     * @see PassengerService#deletePassenger(Long)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        try {
            // Chama o service para deletar o passageiro
            passengerService.deletePassenger(id);
            // Retorna 204 No Content (sucesso, sem corpo de resposta)
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            // Se o service lançou exceção (ex: "Passenger not found"), retorna 404
            return ResponseEntity.notFound().build();
        }

    }

}