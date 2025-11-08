package com.taxiapp.taxi_api.Entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false, length=100)
    private String name;
    
    @Column(unique=true, nullable=false, length=50)
    private String username;
    
    @Column(nullable=false, length=100)
    private String address;
    
    @Column(nullable=false, length=15)
    private String phone;

    @Column(unique=true, nullable=false, length=100)
    private String email;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;


    public Passenger() {
    }

    public Passenger(String name, String username, String adress, String phone, String email) {
        this.name =name;
        this.username = username;
        this.address = adress;
        this.phone = phone;
        this.email = email;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String adress) {
        this.address = adress;
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

