package com.taxiapp.taxi_api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.taxiapp.taxi_api.dto.DriverRequestDTO;
import com.taxiapp.taxi_api.dto.DriverResponseDTO;
import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.model.Passenger;
import com.taxiapp.taxi_api.service.DriverService;
import com.taxiapp.taxi_api.service.PassengerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



/**
 * Controlador REST API para gerenciar a entidade {@link Passenger}.
 *
 * <p>Expõe endpoints CRUD (Create, Read, Update, Delete)
 * para operações com motoristas.</p>
 *
 * <p>@RestController combina @Controller e @ResponseBody,
 * significando que os retornos dos métodos serão serializados (ex: para JSON).</p>
 * <p>@RequestMapping define a URL base "/api/drivers" para todos os
 * endpoints definidos nesta classe.</p>
 *
 * @see PassengerService
 * @see PassengerRequestDTO
 * @see PassengerResponseDTO
 * @author albicr
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    /**
     * Injeção da camada de serviço (Service) que contém as regras de
     * negócio para operações com motoristas.
     */
    @Autowired
    private DriverService driverService;

    /**
     * <h3>Endpoint: CREATE (Criar)</h3>
     * Cria um novo motorista no sistema.
     * 
     * <p>Mapeado para: <code>POST /api/drivers</code></p>
     * 
     * <p>A anotação {@link Valid} ativa as validações
     * (ex: @NotBlank, @Email) definidas no DTO.</p>
     * 
     * @param requestDTO O DTO {@link DriverRequestDTO} contendo os
     * dados do novo motorista.
     * @return O {@link DriverResponseDTO} do motorista recém-criado,
     * incluindo o ID gerado e outros campos preenchidos.
     * @see DriverService#createDriver(DriverRequestDTO)
     */
    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO requestDTO) {
    
    //1. Chama o service para criar o motorista
    DriverResponseDTO response = driverService.createDriver(requestDTO);

    // 2. Retorna uma resposta "201 Created" (boa prática de API)
    // Isso inclui um cabeçalho "Location" com a URL do novo recurso
    return ResponseEntity.created(URI.create("api/drivers/" + response.id())).body(response);
}
    
    /**
     * <h3>Endpoint: READ (Ler por ID)</h3>
     * Busca um motorista específico pelo seu ID.
     * 
     * <p>Mapeado para: <code>GET /api/drivers/{id}</code></p>
     * (ex: /api/drivers/1)</p>
     * 
     * @param id O ID do motorista a ser buscado,
     * fornecido como uma variável de caminho (path variable).
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e o {@link DriverResponseDTO} no corpo, caso encontrado.
     * <p> Retorna status <strong>404 Not Found</strong> se o IS não existir.</p>
     * @see DriverService#getDriverById(Long)
     */
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) {
        // 1. Chama o service para buscar o motorista pelo ID
        Optional<DriverResponseDTO> Driver = driverService.getDriverById(id);

        // 2. Retorna 200 OK com o motorista se encontrado, ou 404 Not Found se não encontrado
        return Driver.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * <h3> Endpoint: READ (Ler todos)</h3>
     * Retorna uma lista com todos os motoristas cadastrados no sistema.
     * 
     * <p>Mapeado para: <code>GET /api/drivers</code></p>
     * 
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e uma lista de {@link DriverResponseDTO} no corpo.
     * <p> Retorna uma lista vazia se não houver motoristas cadastrados.</p>
     * @see DriverService#getAllDrivers()
     */
    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        // 1. Chama o service para obter todos os motoristas
        List<DriverResponseDTO> drivers = driverService.getAllDrivers();

        // 2. Retorna 200 OK com a lista de motoristas (pode estar vazia)
        return ResponseEntity.ok(drivers);
    }

    /**
     * <h3>Endpoint: UPDATE (Atualizar)</h3>
     * Atualiza os dados de um motorista existente.
     * 
     * <p>Mapeado para: <code>PUT /api/drivers/{id}</code></p>
     * 
     * @param id O ID do motorista a ser atualizado, fornecido na URL.
     * @param requestDTO O DTO (JSON) {@link DriverRequestDTO} com os novos dados para o motorista.
     * @return Um {@link ResponseEntity} com status <strong>200 OK</strong>
     * e o {@link DriverResponseDTO} com os dados atualizados.
     * <p> Retorna <strong>404 Not Found</strong> se o ID do 
     * motorista não existir.</p>
     * @see DriverService#updateDriver(Long, DriverRequestDTO)
     */
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequestDTO requestDTO) {
            // 1. Chama o service para atualizar o motorista
            DriverResponseDTO updatedDriver = driverService.updateDriver(id, requestDTO);

            // 2. Retorna 200 OK com o motorista atualizado
            return ResponseEntity.ok(updatedDriver);
    }

    /**
     * <h3>Endpoint: DELETE (Deletar)</h3>
     * Deleta um motorista do sistema pelo seu ID.
     * 
     * Mapeado para: <code>DELETE /api/drivers/{id}</code></p>
     * 
     * @param id O ID do motorista a ser deletado, fornecido na URL.
     * @return Um {@link ResponseEntity} com status <strong>204 No Content</strong>
     * se a deleção for bem-sucedida.
     * <p> Retorna <strong>404 Not Found</strong> se o ID do
     * motorista não existir.</p>
     * @see DriverService#deleteDriver(Long)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        try {
            // 1. Chama o service para deletar o motorista
            driverService.deleteDriver(id);

            // 2. Retorna 204 No Content se a deleção for bem-sucedida
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // Retorna 404 Not Found se o ID do motorista não existir
            return ResponseEntity.notFound().build();
        }
    }










}
