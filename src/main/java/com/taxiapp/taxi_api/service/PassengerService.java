package com.taxiapp.taxi_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.mapper.PassengerMapper;
import com.taxiapp.taxi_api.model.Passenger;
import com.taxiapp.taxi_api.repository.PassengerRepository;


/**
 * Service (camada de lógica de negócio) para a entidade Passenger.
 * A anotação @Service informa ao Spring que esta classe deve ser
 * gerenciada por ele (é um "Bean").
 */
@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

    /**
     * Método CREATE (Criar)
     * Valida os dados, cria um novo passageiro e o salva no banco.
     * Recebe um DTO de requisição e retorna um DTO de resposta.
     *
     * @param requestDTO O DTO (Data Transfer Object) contendo os dados do novo passageiro.
     * @return O DTO de resposta (PassengerResponseDTO) do passageiro salvo (com ID e sem senha).
     * @throws IllegalArgumentException Se o email, username ou telefone já estiverem em uso.
     */
    @Transactional
    public PassengerResponseDTO createPassenger(PassengerRequestDTO requestDTO) {
        // 1. Validação de Negócio (Campos Únicos)
        // (A validação @NotBlank/@Email é feita pelo Controller)
        passengerRepository.findByEmail(requestDTO.getEmail()).ifPresent(p -> {
            throw new IllegalArgumentException("Email already in use");
        });

        passengerRepository.findByUsername(requestDTO.getUsername()).ifPresent(p -> {
            throw new IllegalArgumentException("Username already in use");
        });

        passengerRepository.findByPhone(requestDTO.getPhone()).ifPresent(p -> {
            throw new IllegalArgumentException("Phone number already in use");
        });

        // 2. Conversão do DTO para Entidade
        Passenger newPassenger = passengerMapper.toEntity(requestDTO);

        // 3. Salvamento da Entidade no Banco de Dados
        Passenger savedPassenger = passengerRepository.save(newPassenger);

        // 4. Conversão da Entidade Salva para DTO de Resposta
        return passengerMapper.toResponseDTO(savedPassenger);
    }
    /**
     * Método READ (Ler)
     * Busca um passageiro pelo seu ID.
     * @param id O ID do passageiro a ser buscado.
     * @return O objeto Passenger encontrado, ou null se não encontrado.
     */
    @Transactional (readOnly = true)
    public Optional<PassengerResponseDTO> getPassengerbyId(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toResponseDTO);
    }
    /**
     * Método READ (Ler)
     * Busca um passageiro pelo seu telefone.
     * @param phone O telefone do passageiro a ser buscado.
     * @return O objeto Passenger encontrado, ou null se não encontrado.
     */
    @Transactional (readOnly = true)
    public Optional<PassengerResponseDTO> getPassengerByPhone(String phone) {
        return passengerRepository.findByPhone(phone)
                .map(passengerMapper::toResponseDTO);
        }
    /**
     * READ (Ler Todos)
     * Converte a lista usando o mapper.
     */
    @Transactional (readOnly = true)
    public List<PassengerResponseDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(passengerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    /**
     * UPDATE (Limpo)
     */
    @Transactional
    public PassengerResponseDTO updatePassenger(long id, PassengerRequestDTO requestDTO) {
        // 1. Service method to update a Passenger
        Passenger passengerExisting = passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Passenger with ID " + id + " not found."));            
    
        // 2. Business Validations (update)
        // Email validation
        Optional<Passenger> passengerWithNewEmail = passengerRepository.findByEmail(requestDTO.getEmail());
        if (passengerWithNewEmail.isPresent() && !passengerWithNewEmail.get().getId().equals(id)) {
            // Se um passageiro com este email foi encontrado E o ID dele não é o mesmo do que estamos atualizando...
            throw new IllegalArgumentException("Email " + requestDTO.getEmail() + " is already in use.");
        }

        // Username validation
        Optional<Passenger> passengerWithNewUsername = passengerRepository.findByUsername(requestDTO.getUsername());
        if (passengerWithNewUsername.isPresent() && !passengerWithNewUsername.get().getId().equals(id)) {
            // Se um passageiro com este username foi encontrado E o ID dele não é o mesmo do que estamos atualizando...
            throw new IllegalArgumentException("Username " + requestDTO.getUsername() + " is already in use.");
        }
        
        // Phone validation
        Optional<Passenger> passengerWithNewPhone = passengerRepository.findByPhone(requestDTO.getPhone());
        if (passengerWithNewPhone.isPresent() && !passengerWithNewPhone.get().getId().equals(id)) {
            // Se um passageiro com este phone foi encontrado E o ID dele não é o mesmo do que estamos atualizando...
            throw new IllegalArgumentException("Phone " + requestDTO.getPhone() + " is already in use.");
        }

        // 3. Update Entity using Mapper
        passengerMapper.updateEntityFromDTO(requestDTO, passengerExisting);
    
        // 4. Save updated entity
        Passenger updatedPassenger = passengerRepository.save(passengerExisting);

        // 5. Convert to ResponseDTO and return
        return passengerMapper.toResponseDTO(updatedPassenger);
    }
    /**
     * MÉTODO DELETE (Deletar)
     * Deleta um passageiro pelo seu ID, após verificar se ele existe.
     */
    @Transactional
    public void deletePassenger(long id) {
        // 1. Primeiro, verifica se o passageiro existe
        if (!passengerRepository.existsById(id)) {
             // 2. Se não existe, lança uma exceção
             throw new IllegalStateException("Passenger with ID " + id + " not found.");
        }
        // 3. Se existe, deleta
        passengerRepository.deleteById(id);
    }
}


