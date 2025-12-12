package com.example.demo.repository;

import com.example.demo.model.Vehicle;

import java.util.List;
import java.util.Optional;


public interface VehicleRepository {

    // Get all vehicles
    List<Vehicle> findAll();

    // For dropdown sorted by VIN
    List<Vehicle> findAllOrderedByVin();

    // Find by primary key (registration_no)
    Optional<Vehicle> findById(String registrationNo);

    // Find by registration number (same as primary key, but kept for clarity)
    Optional<Vehicle> findByRegistrationNo(String registrationNo);

    // Find by brand
    List<Vehicle> findByBrand(String brand);

    // Find by brand + model
    List<Vehicle> findByBrandAndModel(String brand, String model);

    // Find by model year
    List<Vehicle> findByModelYear(int modelYear);

    // Insert or update vehicle
    Vehicle save(Vehicle vehicle);

    // Delete by primary key
    void deleteById(String registrationNo);

    // Count all rows
    long count();
}