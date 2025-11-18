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
 * Camada de Serviço (Service Layer) que encapsula a lógica de negócio
 * para a entidade {@link Passenger}.
 *
 * <p>Esta classe é responsável por mediar a comunicação entre o
 * {@link com.taxiapp.taxi_api.controller.PassengerController} e o
 * {@link PassengerRepository}, aplicando validações de negócio,
 * mapeamento de DTOs e gerenciamento de transações.</p>
 *
 * <p>A anotação @Service informa ao Spring que esta classe deve ser
 * gerenciada por ele (é um "Bean").</p>
 *
 * @see PassengerRepository
 * @see PassengerMapper
 * @see Passenger
 * @author [Seu Nome/Equipe]
 * @version 1.0.0
 */
@Service
public class PassengerService {

    /**
     * Injeção do repositório para acesso aos dados dos passageiros.
     */
    @Autowired
    private PassengerRepository passengerRepository;

    /**
     * Injeção do mapper para conversão entre Entidades e DTOs.
     */
    @Autowired
    private PassengerMapper passengerMapper;

    /**
     * Cria um novo passageiro no sistema.
     *
     * <p>Este método é transacional ({@code @Transactional}). Ele primeiro valida
     * se os campos únicos (email, username, phone) já estão em uso. Se a
     * validação for bem-sucedida, ele mapeia o DTO para uma
     * entidade {@link Passenger}, salva-a no banco de dados e
     * retorna o DTO de resposta.</p>
     *
     * @param requestDTO O DTO {@link PassengerRequestDTO} contendo os
     * dados do novo passageiro.
     * @return O {@link PassengerResponseDTO} do passageiro recém-criado,
     * incluindo seu ID gerado.
     * @throws IllegalArgumentException Se o email, username ou
     * telefone já estiverem em uso por outro passageiro.
     */
    @Transactional
    public PassengerResponseDTO createPassenger(PassengerRequestDTO requestDTO) {
        // 1. Validação de Negócio (Campos Únicos)
        passengerRepository.findByEmail(requestDTO.email()).ifPresent(p -> {
            throw new IllegalArgumentException("Email already in use");
        });

        passengerRepository.findByUsername(requestDTO.username()).ifPresent(p -> {
            throw new IllegalArgumentException("Username already in use");
        });

        passengerRepository.findByPhone(requestDTO.phone()).ifPresent(p -> {
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
     * Busca um passageiro pelo seu ID.
     *
     * <p>Esta operação é otimizada para somente leitura
     * ({@code @Transactional(readOnly = true)}).</p>
     *
     * @param id O ID (Long) do passageiro a ser buscado.
     * @return Um {@link Optional} contendo o
     * {@link PassengerResponseDTO} se o passageiro for encontrado,
     * ou {@link Optional#empty()} se não for encontrado.
     */
    @Transactional(readOnly = true)
    public Optional<PassengerResponseDTO> getPassengerbyId(long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toResponseDTO);
    }

    /**
     * Busca um passageiro pelo seu número de telefone.
     *
     * <p>Esta operação é otimizada para somente leitura.</p>
     *
     * @param phone O número de telefone a ser buscado.
     * @return Um {@link Optional} contendo o
     * {@link PassengerResponseDTO} se o passageiro for encontrado,
     * ou {@link Optional#empty()} se não for encontrado.
     */
    @Transactional(readOnly = true)
    public Optional<PassengerResponseDTO> getPassengerByPhone(String phone) {
        return passengerRepository.findByPhone(phone)
                .map(passengerMapper::toResponseDTO);
    }

    /**
     * Retorna uma lista de todos os passageiros cadastrados.
     *
     * <p>Esta operação é otimizada para somente leitura.
     * A lista de entidades é convertida para uma lista de DTOs.</p>
     *
     * @return Uma {@link List} de {@link PassengerResponseDTO}.
     * A lista estará vazia se não houver passageiros cadastrados.
     */
    @Transactional(readOnly = true)
    public List<PassengerResponseDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(passengerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza os dados de um passageiro existente.
     *
     * <p>Este método é transacional. Ele primeiro busca o passageiro
     * pelo ID. Se encontrado, valida se os novos dados
     * (email, username, phone) não conflitam com os de *outros*
     * passageiros. Por fim, o mapper atualiza a entidade existente e
     * ela é salva.</p>
     *
     * @param id O ID (Long) do passageiro a ser atualizado.
     * @param requestDTO O {@link PassengerRequestDTO} com os dados
     * a serem atualizados.
     * @return O {@link PassengerResponseDTO} com os dados atualizados.
     * @throws IllegalStateException Se nenhum passageiro for
     * encontrado com o ID fornecido (lançado pelo orElseThrow).
     * @throws IllegalArgumentException Se o novo email, username ou
     * telefone já estiverem em uso por *outro* passageiro.
     */
    @Transactional
    public PassengerResponseDTO updatePassenger(long id, PassengerRequestDTO requestDTO) {
        // 1. Busca a entidade existente ou lança exceção
        Passenger passengerExisting = passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Passenger with ID " + id + " not found."));

        // 2. Validação de Negócio (Campos únicos, checando se não é o próprio)
        // Email validation
        Optional<Passenger> passengerWithNewEmail = passengerRepository.findByEmail(requestDTO.email());
        if (passengerWithNewEmail.isPresent() && !passengerWithNewEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email " + requestDTO.email() + " is already in use.");
        }

        // Username validation
        Optional<Passenger> passengerWithNewUsername = passengerRepository.findByUsername(requestDTO.username());
        if (passengerWithNewUsername.isPresent() && !passengerWithNewUsername.get().getId().equals(id)) {
            throw new IllegalArgumentException("Username " + requestDTO.username() + " is already in use.");
        }

        // Phone validation
        Optional<Passenger> passengerWithNewPhone = passengerRepository.findByPhone(requestDTO.phone());
        if (passengerWithNewPhone.isPresent() && !passengerWithNewPhone.get().getId().equals(id)) {
            throw new IllegalArgumentException("Phone " + requestDTO.phone() + " is already in us e.");
        }

        // 3. Atualiza a entidade usando o Mapper
        passengerMapper.updateEntityFromDTO(requestDTO, passengerExisting);

        // 4. Salva a entidade atualizada
        Passenger updatedPassenger = passengerRepository.save(passengerExisting);

        // 5. Converte para DTO de resposta e retorna
        return passengerMapper.toResponseDTO(updatedPassenger);
    }

    /**
     * Deleta um passageiro do sistema pelo seu ID.
     *
     * <p>Este método é transacional. Ele primeiro verifica se o
     * passageiro existe antes de tentar a deleção.</p>
     *
     * @param id O ID (Long) do passageiro a ser deletado.
     * @throws IllegalStateException Se nenhum passageiro for
     * encontrado com o ID fornecido (lançado pela verificação 'existsById').
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