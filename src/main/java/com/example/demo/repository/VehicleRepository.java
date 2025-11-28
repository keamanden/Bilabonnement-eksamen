package com.example.demo.repository;

import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    // Find vehicle by registration number
    Optional<Vehicle> findByRegistrationNo(String registrationNo);

    // Find vehicles by brand
    List<Vehicle> findByBrand(String brand);

    // Find vehicles by brand and model
    List<Vehicle> findByBrandAndModel(String brand, String model);

    // Find vehicles by model year
    List<Vehicle> findByModelYear(int modelYear);
}