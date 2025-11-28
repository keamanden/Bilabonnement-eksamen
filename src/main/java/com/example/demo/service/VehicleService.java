package com.example.demo.service;

import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Optional<Vehicle> getVehicleById(int id) {
        return vehicleRepository.findById(id);
    }

    // Get vehicle by registration number
    public Optional<Vehicle> getVehicleByRegistrationNo(String registrationNo) {
        return vehicleRepository.findByRegistrationNo(registrationNo);
    }

    // Get vehicles by brand
    public List<Vehicle> getVehiclesByBrand(String brand) {
        return vehicleRepository.findByBrand(brand);
    }

    // Get vehicles by brand and model
    public List<Vehicle> getVehiclesByBrandAndModel(String brand, String model) {
        return vehicleRepository.findByBrandAndModel(brand, model);
    }

    // Get vehicles by model year
    public List<Vehicle> getVehiclesByModelYear(int modelYear) {
        return vehicleRepository.findByModelYear(modelYear);
    }

    // Create new vehicle
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Update vehicle
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Delete vehicle
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    // Count total vehicles
    public long countVehicles() {
        return vehicleRepository.count();
    }
}