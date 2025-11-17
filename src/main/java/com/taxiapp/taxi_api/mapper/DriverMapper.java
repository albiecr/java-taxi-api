package com.taxiapp.taxi_api.mapper;

import org.springframework.stereotype.Component;

import com.taxiapp.taxi_api.dto.DriverRequestDTO;
import com.taxiapp.taxi_api.dto.DriverResponseDTO;
import com.taxiapp.taxi_api.model.Driver;

@Component
public class DriverMapper {
    
    public Driver toEntity(DriverRequestDTO requestDTO) {
        Driver driver = new Driver();
        
        driver.setName(requestDTO.name());
        driver.setLicenseNumber(requestDTO.licenseNumber());
        driver.setAddress(requestDTO.address());
        driver.setPhone(requestDTO.phone());
        driver.setVehiclePlate(requestDTO.vehiclePlate());

        return driver;
    }

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
    
    public void updateEntityFromDTO(DriverRequestDTO requestDTO, Driver driver) {
        driver.setName(requestDTO.name());
        driver.setLicenseNumber(requestDTO.licenseNumber());
        driver.setAddress(requestDTO.address());
        driver.setPhone(requestDTO.phone());
        driver.setVehiclePlate(requestDTO.vehiclePlate());
    }
}
