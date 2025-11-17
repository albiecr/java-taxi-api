package com.taxiapp.taxi_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para encapsular os dados de uma requisição
 * de criação ou atualização de um Passageiro (Passenger).
 *
 * <p>Esta classe é usada para receber dados da API (ex: JSON em um POST/PUT)
 * e aplicar validações de constraints.</p>
 *
 * @param name     Nome completo do passageiro (não pode ser nulo/vazio).
 * @param username Nome de usuário único (mínimo 4 caracteres, não pode ser nulo/vazio).
 * @param address  Endereço principal (não pode ser nulo/vazio).
 * @param phone    Telefone de contato (não pode ser nulo/vazio).
 * @param email    E-mail único (formato válido, não pode ser nulo/vazio).
 *
 * @see com.taxiapp.taxi_api.model.Passenger
 * @see com.taxiapp.taxi_api.controller.PassengerController
 */
public record PassengerRequestDTO(
    
    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, message = "Username must be at least 4 characters long")
    String username,

    @NotBlank(message = "Address cannot be blank")
    String address,

    @NotBlank(message = "Phone cannot be blank")
    String phone,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email
) {
    // Nenhum getter, setter, construtor ou equals/hashCode/toString é necessário.
    // O Javadoc para os parâmetros acima documenta os componentes do record.
}