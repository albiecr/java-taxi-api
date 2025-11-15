package com.taxiapp.taxi_api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.service.PassengerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * Controller (camada de API) para a entidade Passenger.
 * @RestController combina @Controller e @ResponseBody, 
 * significando que os retornos dos métodos serão JSON.
 * @RequestMapping define a URL base para todos os métodos nesta classe.
 */
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    
    //Injeta o "cérebro" (Service) para usar os métodos de negócio.
    @Autowired
    private PassengerService passengerService;

    /**
     * CREATE (Criar)
     * Mapeado para: POST http://localhost:8080/api/passengers
     * @Valid ativa as validações (@NotBlank, @Email) no DTO.
     */
    @PostMapping
    public ResponseEntity<PassengerResponseDTO> createPAssenger(
        @Valid @RequestBody PassengerRequestDTO requestDTO
    ) {
        // 1. Chama o service para criar o passageiro
        PassengerResponseDTO response = passengerService.createPassenger(requestDTO);

        // 2. Retorna uma resposta "201 Created" (boa prática de API)
        // Isso inclui um cabeçalho "Location" com a URL do novo recurso
        return ResponseEntity.created(URI.create("/api/passengers/" + response.getId())).body(response);
    }
    /**
     * READ (Ler por ID)
     * Mapeado para: GET http://localhost:8080/api/passengers/1 (ou 2, 3, etc.)
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
     * READ (Ler Todos)
     * Mapeado para: GET http://localhost:8080/api/passengers
     */
    @GetMapping
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers() {
        // Retorna 200 OK com a lista (pode ser vazia)
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }
    /**
     * UPDATE (Atualizar)
     * Mapeado para: PUT http://localhost:8080/api/passengers/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO requestDTO) {
        try {
            // Chama o service para atualizar o passageiro
            PassengerResponseDTO updatedPassenger = passengerService.updatePassenger(id, requestDTO);
    
            // Retorna 200 OK (se atualizou) ou 404 Not Found (se não encontrou)
            return ResponseEntity.ok(updatedPassenger);
        } catch (IllegalArgumentException e) {
            // Se o service lançou exceção (ex: "Passenger not found"), retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * DELETE (Deletar)
     * Mapeado para: DELETE http://localhost:8080/api/passengers/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        try {
            // Chama o service para deletar o passageiro
            passengerService.deletePassenger(id);
            // Retorna 204 No Content (sucesso, sem corpo de resposta)
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            // Se o service lançou exceção (ex: "Passenger not found"), retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
        
    }
    
}
