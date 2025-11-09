package com.taxiapp.taxiapi.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String pickupLocation;

    @Column(nullable=false, length=100)
    private String dropoffLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=15)
    private RideStatus status;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="passenger_id", nullable=false)
    private Passenger passenger;
    
    @ManyToOne
    @JoinColumn(name="driver_id", nullable=true)        // driver can be null when ride is requested 
    private Driver driver;

    public Ride() {
    }

   public Ride(Passenger passenger, String pickupLocation, String dropoffLocation) {
        this.passenger = passenger;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = RideStatus.REQUESTED;             // automatically set to REQUESTED
        this.driver = null;                             // automatically null when created
                                                        // 'createdAt' field is automatically set by the @CreationTimestamp annotation.
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickupLocation() {
        return this.pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return this.dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public RideStatus getStatus() {
        return this.status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    

}