package com.taxiapp.taxi_api.mapper;

import org.springframework.stereotype.Component;

import com.taxiapp.taxi_api.dto.PassengerRequestDTO;
import com.taxiapp.taxi_api.dto.PassengerResponseDTO;
import com.taxiapp.taxi_api.model.Passenger;

/**
 * Classe Mapper para converter entre Entidade Passenger e seus DTOs.
 * @Component faz desta classe um "Bean" do Spring,
 * para que possamos injetá-la (@Autowired) no nosso Service.
 */
@Component
public class PassengerMapper {
    /**
     * Converte um DTO de Requisição (dados de entrada) em uma Entidade Passenger.
     * Usado ao CRIAR um novo passageiro.
     */
    public Passenger toEntity(PassengerRequestDTO requestDTO) {
        // Usamos o construtor criado na entidade Passenger
        return new Passenger(
            requestDTO.getName(),
            requestDTO.getUsername(),
            requestDTO.getAddress(),
            requestDTO.getPhone(),
            requestDTO.getEmail()
        );
    }
    /**
     * Converte uma Entidade Passenger (do banco) em um DTO de Resposta (dados de saída).
     * Usado ao ENVIAR o passageiro de volta para o front-end.
     */
    public PassengerResponseDTO toResponseDTO(Passenger passenger) {
        // Usamos o construtor criado no DTO de Resposta
        return new PassengerResponseDTO(passenger);
    }
    /**
     * Atualiza uma Entidade Passenger existente com dados de um DTO de Requisição.
     * Usado ao ATUALIZAR um passageiro.
     */
    public void updateEntityFromDTO(PassengerRequestDTO requestDTO, Passenger passenger) {
        passenger.setName(requestDTO.getName());
        passenger.setUsername(requestDTO.getUsername());
        passenger.setAddress(requestDTO.getAddress());
        passenger.setPhone(requestDTO.getPhone());
        passenger.setEmail(requestDTO.getEmail());     
    }
}
