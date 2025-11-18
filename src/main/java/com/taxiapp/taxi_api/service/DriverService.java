package com.taxiapp.taxi_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxiapp.taxi_api.dto.DriverRequestDTO;
import com.taxiapp.taxi_api.dto.DriverResponseDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.mapper.DriverMapper;
import com.taxiapp.taxi_api.model.Driver;
import com.taxiapp.taxi_api.repository.DriverRepository;

/**
 * Camada de Serviço (Service Layer) para a entidade {@link Driver}.
 * <p>
 * Esta classe contém toda a <strong>lógica de negócio</strong> relacionada aos motoristas.
 * Ela atua como um orquestrador, utilizando o {@link DriverRepository} para acessar
 * o banco de dados e o {@link DriverMapper} para converter entre Entidades e DTOs.
 * <p>
 * A anotação {@code @Service} a marca como um Bean do Spring.
 * <p>
 * {@code @RequiredArgsConstructor} (do Lombok) gera um construtor para todos os
 * campos {@code final}, permitindo a injeção de dependência limpa.
 * <p>
 * {@code @Transactional} garante que todos os métodos públicos sejam executados
 * dentro de uma transação de banco de dados, assegurando a integridade dos dados (commit/rollback).
 *
 * @see com.taxiapp.taxi_api.repository.DriverRepository
 * @see com.taxiapp.taxi_api.mapper.DriverMapper
 * @see com.taxiapp.taxi_api.exception.ResourceNotFoundException
 */
@Service
@Transactional
public class DriverService {
    
    /**
     * Injeção do repositório para acesso aos dados dos dados dos motoristas.
     */
    @Autowired
    private DriverRepository driverRepository;

    /**
     * Injeção do mapper para conversão entre Entidades e DTOs.
     */
    @Autowired
    
    private DriverMapper  driverMapper;

   /**
     * Cria um novo motorista no sistema.
     *
     * <p>Este método é transacional ({@code @Transactional}). Ele primeiro valida
     * se os campos únicos (licenseNumber, vehiclePlate) já estão em uso. Se a
     * validação for bem-sucedida, ele mapeia o DTO para uma
     * entidade {@link Driver}, define o motorista como 'disponível' (available = true),
     * salva-o no banco de dados e retorna o DTO de resposta.</p>
     *
     * @param requestDTO O DTO {@link DriverRequestDTO} contendo os
     * dados do novo motorista.
     * @return O {@link DriverResponseDTO} do motorista recém-criado,
     * incluindo seu ID gerado.
     * @throws IllegalArgumentException Se o licenseNumber ou
     * vehiclePlate já estiverem em uso por outro motorista.
     */
    @Transactional
    public DriverResponseDTO createDriver(DriverRequestDTO requestDTO) {
        // 1. Validação de Negócio (Campos Únicos)
        driverRepository.findByLicenseNumber(requestDTO.licenseNumber()).ifPresent( d -> {
            throw new IllegalArgumentException("License Number already in use.");   
        });

        driverRepository.findByVehiclePlate(requestDTO.vehiclePlate()).ifPresent( d -> {
            throw new IllegalArgumentException("Vehicle Plate already in use.");   
        });

        // 2. Conversão do DTO para Entidade
        Driver newDriver = driverMapper.toEntity(requestDTO);

        // 3. Salvamento da Entidade no Banco de Dados
        Driver savedDriver = driverRepository.save(newDriver);

        // 4. Conversão da Entidade Salva para DTO de Resposta
        return driverMapper.toResponseDTO(savedDriver);
    }
    
    /**
     * Busca um motorista pelo seu ID.
     *
     * <p>Esta operação é otimizada para somente leitura
     * ({@code @Transactional(readOnly = true)}).</p>
     *
     * @param id O ID (Long) do motorista a ser buscado.
     * @return Um {@link Optional} contendo o
     * {@link DriverResponseDTO} se o motorista for encontrado,
     * ou {@link Optional#empty()} se não for encontrado.
     */
    @Transactional(readOnly = true)
    public Optional<DriverResponseDTO> getDriverById(long id) {
        return driverRepository.findById(id)
            .map(driverMapper::toResponseDTO);

    }

    /**
     * Busca um motorista pelo número da sua CNH (License Number).
     *
     * <p>Esta operação é otimizada para somente leitura.</p>
     *
     * @param licenseNumber O número da CNH a ser buscada.
     * @return Um {@link Optional} contendo o
     * {@link DriverResponseDTO} se o motorista for encontrado,
     * ou {@link Optional#empty()} se não for encontrado.
     */
    @Transactional(readOnly = true)
    public Optional<DriverResponseDTO> getDriverByLicenseNumber(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber)
            .map(driverMapper::toResponseDTO);
    }

    /**
     * Retorna uma lista de todos os motoristas cadastrados.
     *
     * <p>Esta operação é otimizada para somente leitura.
     * A lista de entidades é convertida para uma lista de DTOs.</p>
     *
     * @return Uma {@link List} de {@link PassengerResponseDTO}.
     * A lista estará vazia se não houver passageiros cadastrados.
     */
    @Transactional(readOnly = true)
    public List<DriverResponseDTO> getAllDrivers() {
        return driverRepository.findAll()
            .stream()
            .map(driverMapper::toResponseDTO)
            .collect(Collectors.toList());

    }

    /**
     * Atualiza os dados de um motorista existente.
     *
     * <p>Este método é transacional. Ele primeiro busca o motorista
     * pelo ID. Se encontrado, valida se os novos dados
     * (licenseNumber, vehiclePlate) não conflitam com os de *outros*
     * motoristas. Por fim, o mapper atualiza a entidade existente e
     * ela é salva.</p>
     *
     * @param id O ID (Long) do motorista a ser atualizado.
     * @param requestDTO O {@link DriverRequestDTO} com os dados
     * a serem atualizados.
     * @return O {@link DriverResponseDTO} com os dados atualizados.
     * @throws IllegalStateException Se nenhum motorista for
     * encontrado com o ID fornecido (lançado pelo orElseThrow).
     * @throws IllegalArgumentException Se o novo licenseNumber ou
     * vehiclePlate já estiverem em uso por *outro* motorista.
     */
   @Transactional
    public DriverResponseDTO updateDriver(long id, DriverRequestDTO requestDTO) {
        // 1. Busca do Motorista Existente
        Driver existingDriver = driverRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Driver with ID " + id + " not found."));
        
        // 2. Validação de Negócio (Campos Únicos, checando se não é o próprio)
        // License Number Validation
        Optional<Driver> driverByLicense = driverRepository.findByLicenseNumber(requestDTO.licenseNumber());
        if (driverByLicense.isPresent() && driverByLicense.get().getId() !=id) {
            throw new IllegalArgumentException("License Number " + requestDTO.licenseNumber() + " already in use.");
        }    

        // Vehicle Plate Validation
        Optional<Driver> driverByPlate = driverRepository.findByLicenseNumber(requestDTO.vehiclePlate());
        if (driverByPlate.isPresent() && driverByPlate.get().getId() !=id) {
            throw new IllegalArgumentException("Vehicle Plate " + requestDTO.vehiclePlate() + " already in use.");
        }

        // 3. Atualiza a entidade usando o Mapper
        driverMapper.updateEntityFromDTO(requestDTO, existingDriver);

        // 4. Salva a entidade atualizada no banco de dados
        Driver updateDriver = driverRepository.save(existingDriver);

        // 5. Converte a entidade atualizada para DTO de resposta
        return driverMapper.toResponseDTO(updateDriver);
    }

    /**
     * Deleta um motorista do sistema pelo seu ID.
     *
     * <p>Este método é transacional. Ele primeiro verifica se o
     * passageiro existe antes de tentar a deleção.</p>
     *
     * @param id O ID (Long) do motorista a ser deletado.
     * @throws IllegalStateException Se nenhum passageiro for
     * encontrado com o ID fornecido (lançado pela verificação 'existsById').
     */
    @Transactional
    public void deleteDriver(long id) {
    // 1. Verifica se o motorista existe
    if(!driverRepository.existsById(id)) {
        // 2. Se não existir, lança exceção
        throw new IllegalStateException("Driver with ID " + id + " not found.");
    }
    // 3. Se existir, deleta oo motorista
    driverRepository.deleteById(id);
    }    
}