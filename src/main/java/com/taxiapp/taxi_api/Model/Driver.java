package com.taxiapp.taxi_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa a entidade Motorista (Driver) no banco de dados.
 * <p>
 * Esta classe é mapeada para a tabela 'driver' (ou similar, dependendo da
 * estratégia de nomenclatura) e contém as informações principais de um motorista.
 */
@Entity
public class Driver {

    /**
     * Identificador único do motorista, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do motorista. Não pode ser nulo.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Número da licença (CNH). É único e não pode ser nulo.
     */
    @Column(unique = true, nullable = false, length = 9)
    private String licenseNumber;

    /**
     * Endereço residencial do motorista. Não pode ser nulo.
     */
    @Column(nullable = false, length = 100)
    private String address;

    /**
     * Número de telefone para contato. Não pode ser nulo.
     */
    @Column(nullable = false, length = 11)
    private String phone;

    /**
     * Placa do veículo associado ao motorista. É única e não pode ser nula.
     */
    @Column(unique = true, nullable = false, length = 7)
    private String vehiclePlate;

    /**
     * Status de disponibilidade do motorista (true = disponível, false = indisponível).
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private Boolean available;

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public Driver() {
    }

    /**
     * Construtor para criar uma nova instância de Driver com todos os campos.
     *
     * @param name          Nome do motorista.
     * @param licenseNumber Número da CNH.
     * @param address       Endereço.
     * @param phone         Telefone.
     * @param vehiclePlate  Placa do veículo.
     * @param available     Status de disponibilidade.
     */
    public Driver(String name, String licenseNumber, String address, String phone, String vehiclePlate, Boolean available) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.phone = phone;
        this.vehiclePlate = vehiclePlate;
        this.available = available;
    }

    // --- Getters e Setters ---

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

    public String getLicenseNumber() {
        return this.licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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

    public String getVehiclePlate() {
        return this.vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    /**
     * Verifica se o motorista está disponível.
     * @return true se disponível, false caso contrário.
     */
    public Boolean isAvailable() {
        return this.available;
    }

    /**
     * Retorna o status de disponibilidade (wrapper Boolean).
     * @return O status de disponibilidade.
     */
    public Boolean getAvailable() {
        return this.available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}