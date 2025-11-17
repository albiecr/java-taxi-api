package com.taxiapp.taxi_api.dto;

/**
 * Data Transfer Object (DTO) para enviar informações de um Motorista ao cliente.
 * <p>
 * Este DTO é usado como um objeto de resposta, contendo apenas os dados
 * que são seguros e necessários para exibição no frontend.
 * <p>
 * Utiliza um Java Record para imutabilidade e redução de boilerplate.
 *
 * @param id            O identificador único do motorista.
 * @param name          O nome completo do motorista.
 * @param licenseNumber O número da CNH do motorista.
 * @param address       O endereço do motorista.
 * @param phone         O telefone do motorista.
 * @param vehiclePlate  A placa do veículo.
 * @param available     O status de disponibilidade (true = disponível).
 */
public record DriverResponseDTO(
    Long id,
    String name,
    String licenseNumber,
    String address,
    String phone,
    String vehiclePlate,
    Boolean available
) {
    // Getters, construtor, toString, equals e hashCode são gerados automaticamente.
}