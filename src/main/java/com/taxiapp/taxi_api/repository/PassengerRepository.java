package com.taxiapp.taxi_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxiapp.taxi_api.model.Passenger;



@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByName(String name);
    Optional<Passenger> findByPhone(String phone);
    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findByUsername(String username);
}
