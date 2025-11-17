package com.taxiapp.taxi_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) para receber dados de criação ou atualização de um Motorista.
 * <p>
 * Utiliza Java Records para imutabilidade e redução de boilerplate (getters,
 * toString, equals, hashCode são gerados automaticamente).
 * As validações garantem que os dados estão formatados corretamente antes de
 * chegarem à camada de serviço.
 *
 * @param name          Nome completo (3 a 100 caracteres).
 * @param licenseNumber Número da CNH (máx 9 caracteres).
 * @param address       Endereço (máx 100 caracteres). (Corrigido de "adress")
 * @param phone         Telefone (máx 11 caracteres).
 * @param vehiclePlate  Placa do veículo (exatos 7 caracteres).
 */
public record DriverRequestDTO(

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name,

    @NotBlank(message = "License Number cannot be blank")
    @Size(max = 9, message = "License Number must not exceed 9 characters")
    String licenseNumber,

    // 🚨 CORREÇÃO CRÍTICA: "adress" foi corrigido para "address"
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address must not exceed 100 characters")
    String address,

    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 11, message = "Phone must not exceed 11 characters")
    String phone,

    @NotBlank(message = "Vehicle Plate cannot be blank")
    @Size(min = 7, max = 7, message = "Vehicle Plate must be exactly 7 characters")
    String vehiclePlate

) {
    // Nenhum getter ou setter manual é necessário porque usei o record!
}