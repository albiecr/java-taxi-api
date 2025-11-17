package com.taxiapp.taxi_api.dto;

import java.time.LocalDateTime;

import com.taxiapp.taxi_api.model.Passenger;

/**
 * DTO (Data Transfer Object) para enviar os dados de um Passageiro
 * de volta ao cliente (front-end).
 *
 * <p>Como um 'record', esta classe é imutável e concisa,
 * expondo apenas os dados seguros da entidade.</p>
 *
 * @param id        O ID único do passageiro.
 * @param name      O nome completo do passageiro.
 * @param username  O nome de usuário (login) do passageiro.
 * @param address   O endereço principal do passageiro.
 * @param phone     O telefone de contato do passageiro.
 * @param email     O e-mail de contato do passageiro.
 * @param createdAt A data e hora em que a conta foi criada.
 *
 * @see com.taxiapp.taxi_api.model.Passenger
 */
public record PassengerResponseDTO(
    Long id,
    String name,
    String username,
    String address,
    String phone,
    String email,
    LocalDateTime createdAt
) {
    
    /**
     * Método factory estático para mapear de uma entidade Passenger.
     * <p>Esta é a forma idiomática de converter uma Entidade
     * em um DTO de 'record'.</p>
     *
     * @param passenger A entidade {@link Passenger} vinda do banco de dados.
     * @return Uma nova instância de {@link PassengerResponseDTO} com os dados mapeados.
     */
    public static PassengerResponseDTO fromEntity(Passenger passenger) {
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
}