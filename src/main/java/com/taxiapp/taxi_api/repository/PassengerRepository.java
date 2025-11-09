package com.taxiapp.taxi_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxiapp.taxi_api.model.Passenger;



@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {       // JpaRepository already comes with all the CRUD methods ready.
    Optional<Passenger> findByName(String name);                                    // Custom query methods based on field names
    Optional<Passenger> findByPhone(String phone);
    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findByUsername(String username);
}
