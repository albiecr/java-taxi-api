package com.taxiapp.taxi_api.dto;

import java.time.LocalDateTime;

import com.taxiapp.taxi_api.model.Passenger;

/**
 * DTO (Data Transfer Object) para enviar os dados de um Passageiro
 * de volta ao cliente (front-end).
 * * Note que este objeto NÃO inclui o campo 'password', por segurança.
 */
public class PassengerResponseDTO {

    private Long id;
    private String name;
    private String username;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime createdAt;

    /**
     * Construtor padrão (vazio)
     * O Spring precisa disso para alguns processos internos.
     */
    public PassengerResponseDTO() {
    }

    /**
     * Construtor de Mapeamento.
     * Recebe a entidade 'Passenger' e "mapeia" os campos
     * desta entidade para os campos do DTO.
     * @param passenger A entidade vinda do banco de dados.
     */
    public PassengerResponseDTO(Passenger passenger) {
        this.id = passenger.getId();
        this.name = passenger.getName();
        this.username = passenger.getUsername();
        this.address = passenger.getAddress();
        this.phone = passenger.getPhone();
        this.email = passenger.getEmail();
        this.createdAt = passenger.getCreatedAt();
    }

    // --- Getters e Setters ---
    // O Spring (Jackson) usa estes métodos para converter o objeto em JSON.


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
 
}