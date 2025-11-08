package com.taxiapp.taxi_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column (nullable=false, length=100)
    private String name;

    @Column (unique=true, nullable=false, length=50)
    private String licenseNumber;

    @Column (nullable=false, length=100)
    private String address;

    @Column (nullable=false, length=15)
    private String phone;

    @Column(unique=true, nullable=false, length=7)
    private String vehiclePlate;

    @Column(nullable=false)
    private Boolean available;

    public Driver() {    
    }

    public Driver(String name, String licenseNumber, String address, String phone, String vehiclePlate, Boolean available) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.phone = phone;
        this.vehiclePlate = vehiclePlate;
        this.available = available;
    }

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

    public Boolean isAvailable() {
        return this.available;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
}
