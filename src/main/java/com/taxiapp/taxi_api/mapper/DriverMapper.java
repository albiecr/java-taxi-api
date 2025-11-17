package com.taxiapp.taxi_api.mapper;

import org.springframework.stereotype.Component;

import com.taxiapp.taxi_api.dto.DriverRequestDTO;
import com.taxiapp.taxi_api.dto.DriverResponseDTO;
import com.taxiapp.taxi_api.model.Driver;

/**
 * Classe Mapper responsável pela conversão de dados entre a entidade
 * {@link Driver} e seus DTOs (Data Transfer Objects).
 *
 * <p>A anotação {@code @Component} marca esta classe como um "Bean"
 * gerenciado pelo Spring, permitindo que ela seja injetada
 * (via {@code @Autowired}) em outras classes, como o
 * {@link com.taxiapp.taxi_api.service.DriverService}.</p>
 *
 * @see Driver
 * @see DriverRequestDTO
 * @see DriverResponseDTO
 * @author [Seu Nome/Equipe]
 * @version 1.0.0
 */
@Component
public class DriverMapper {

    /**
     * Converte um DTO de Requisição ({@link DriverRequestDTO})
     * em uma nova entidade {@link Driver}.
     *
     * <p>Este método é usado principalmente ao <strong>CRIAR</strong>
     * um novo motorista, antes de salvá-lo no banco de dados.</p>
     *
     * @param requestDTO O DTO (record) de entrada contendo os dados
     * do novo motorista.
     * @return Uma nova instância da entidade {@link Driver},
     * preenchida com os dados do DTO.
     */
    public Driver toEntity(DriverRequestDTO requestDTO) {
        Driver driver = new Driver();

        driver.setName(requestDTO.name());
        driver.setLicenseNumber(requestDTO.licenseNumber());
        driver.setAddress(requestDTO.address());
        driver.setPhone(requestDTO.phone());
        driver.setVehiclePlate(requestDTO.vehiclePlate());

        return driver;
    }

    /**
     * Converte uma entidade {@link Driver} (vinda do banco)
     * em um DTO de Resposta ({@link DriverResponseDTO}).
     *
     * <p>Este método é usado ao <strong>ENVIAR</strong> dados do
     * motorista de volta ao cliente (front-end), garantindo que
     * apenas dados seguros sejam expostos.</p>
     *
     * @param driver A entidade {@link Driver} a ser convertida.
     * @return Um {@link DriverResponseDTO} (record) preenchido com
     * os dados da entidade.
     */
    public DriverResponseDTO toResponseDTO(Driver driver) {

        return new DriverResponseDTO(
                driver.getId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getAddress(),
                driver.getPhone(),
                driver.getVehiclePlate(),
                driver.getAvailable()
        );

    }

    /**
     * Atualiza uma entidade {@link Driver} existente com os dados
     * de um {@link DriverRequestDTO}.
     *
     * <p>Este método é usado ao <strong>ATUALIZAR</strong> um motorista.
     * Ele modifica a entidade original (passada por referência)
     * em vez de criar uma nova.</p>
     *
     * @param requestDTO O DTO (record) de entrada contendo os
     * dados atualizados.
     * @param driver A entidade {@link Driver} existente
     * (vinda do banco) que será modificada.
     */
    public void updateEntityFromDTO(DriverRequestDTO requestDTO, Driver driver) {
        driver.setName(requestDTO.name());
        driver.setLicenseNumber(requestDTO.licenseNumber());
        driver.setAddress(requestDTO.address());
        driver.setPhone(requestDTO.phone());
        driver.setVehiclePlate(requestDTO.vehiclePlate());
    }
}

