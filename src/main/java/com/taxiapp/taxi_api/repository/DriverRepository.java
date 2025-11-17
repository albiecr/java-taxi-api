package com.taxiapp.taxi_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxiapp.taxi_api.model.Driver;

/**
 * Abstrai a camada de persistência para a entidade {@link Driver}.
 * <p>
 * Esta interface é responsável por todas as interações com o banco de dados
 * referentes aos motoristas (ex: salvar, buscar, deletar). É injetada
 * primariamente no {@code DriverService}. (Ou use o link completo abaixo)
 *
 * @see com.taxiapp.taxi_api.service.DriverService
 */

public interface  DriverRepository extends JpaRepository<Driver, Long> {
    
    /**
     * Busca um motorista pelo número da sua licença (CNH).
     * @param licenseNumber O número da licença a ser buscada.
     * @return um Optional contendo o Driver se encontrado, ou vazio caso contrário.
     */
    Optional<Driver> findByLicenseNumber(String licenseNumber);

    /**
     * Busca um motorista pelo seu endereço de e-mail.
     * @param email O e-mail a ser buscado.
     * @return um Optional contendo o Driver se encontrado, ou vazio caso contrário.
     */
    Optional<Driver> findByEmail(String email);
    
}
