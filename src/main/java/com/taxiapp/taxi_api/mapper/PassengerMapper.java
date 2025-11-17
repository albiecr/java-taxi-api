package com.taxiapp.taxi_api.mapper;

import org.springframework.stereotype.Component;

import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.model.Passenger;

/**
 * Classe Mapper responsável pela conversão de dados entre a entidade
 * {@link Passenger} e seus DTOs (Data Transfer Objects).
 *
 * <p>A anotação {@code @Component} marca esta classe como um "Bean"
 * gerenciado pelo Spring, permitindo que ela seja injetada
 * (via {@code @Autowired}) em outras classes, como o
 * {@link com.taxiapp.taxi_api.service.PassengerService}.</p>
 *
 * @see Passenger
 * @see PassengerRequestDTO
 * @see PassengerResponseDTO
 * @author [Seu Nome/Equipe]
 * @version 1.0.0
 */
@Component
public class PassengerMapper {

    /**
     * Converte um DTO de Requisição ({@link PassengerRequestDTO})
     * em uma nova entidade {@link Passenger}.
     *
     * <p>Este método é usado principalmente ao <strong>CRIAR</strong>
     * um novo passageiro, antes de salvá-lo no banco de dados.</p>
     *
     * @param requestDTO O DTO de entrada contendo os dados do novo passageiro.
     * @return Uma nova instância da entidade {@link Passenger},
     * preenchida com os dados do DTO.
     */
    public Passenger toEntity(PassengerRequestDTO requestDTO) {
        // Usamos o construtor criado na entidade Passenger
        return new Passenger(
                requestDTO.name(),
                requestDTO.username(),
                requestDTO.address(),
                requestDTO.phone(),
                requestDTO.email());
    }

    /**
     * Converte uma entidade {@link Passenger} (vinda do banco)
     * em um DTO de Resposta ({@link PassengerResponseDTO}).
     *
     * <p>Este método é usado ao <strong>ENVIAR</strong> dados do
     * passageiro de volta ao cliente (front-end), garantindo que
     * apenas dados seguros sejam expostos.</p>
     *
     * @param passenger A entidade {@link Passenger} a ser convertida.
     * @return Um {@link PassengerResponseDTO} preenchido com os
     * dados da entidade.
     */
    public PassengerResponseDTO toResponseDTO(Passenger passenger) {
        // Chama o construtor do record passando todos os campos
        return new PassengerResponseDTO(
            passenger.getId(),
            passenger.getName(),
            passenger.getUsername(),
            passenger.getAddress(),
            passenger.getPhone(),
            passenger.getEmail(),
            passenger.getCreatedAt()
        );
    }

    /**
     * Atualiza uma entidade {@link Passenger} existente com os dados
     * de um {@link PassengerRequestDTO}.
     *
     * <p>Este método é usado ao <strong>ATUALIZAR</strong> um passageiro.
     * Ele modifica a entidade original (passada por referência)
     * em vez de criar uma nova.</p>
     *
     * @param requestDTO O DTO de entrada contendo os dados
     * atualizados.
     * @param passenger  A entidade {@link Passenger} existente
     * (vinda do banco) que será modificada.
     */
    public void updateEntityFromDTO(PassengerRequestDTO requestDTO, Passenger passenger) {
        passenger.setName(requestDTO.name());
        passenger.setUsername(requestDTO.username());
        passenger.setAddress(requestDTO.address());
        passenger.setPhone(requestDTO.phone());
        passenger.setEmail(requestDTO.email());
    }
}
