package com.taxiapp.taxi_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Método CREATE (Criar)
     * Salva um novo passageiro no banco de dados.
     * @param passenger O objeto Passenger a ser salvo.
     * @return O objeto Passenger salvo (com o ID preenchido).
     */
    public Passenger createPassenger(Passenger passenger) {
        // Null fields checks
        if (passenger.getUsername() == null || passenger.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (passenger.getEmail() == null || passenger.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        if (passenger.getPhone() == null || passenger.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        // Unique field checks
        passengerRepository.findByUsername(passenger.getUsername()).ifPresent(p -> {
            throw new IllegalArgumentException("Username already in use");
        });

        passengerRepository.findByEmail(passenger.getEmail()).ifPresent(p -> {
            throw new IllegalArgumentException("Email already in use");
        });

        passengerRepository.findByPhone(passenger.getPhone()).ifPresent(p -> {
            throw new IllegalArgumentException("Phone already in use");
        });

        return passengerRepository.save(passenger);  

    }
}


