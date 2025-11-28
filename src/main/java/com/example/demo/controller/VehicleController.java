package com.example.demo.controller;

import com.example.demo.model.Vehicle;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // SIMPLE VERSION - NODet  LOGIN REQUIRED
    // Just show the vehicle list directly

    @GetMapping
    public String listVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("totalVehicles", vehicles.size());
        return "vehicle-list";
    }

    // Show vehicle details
    @GetMapping("/{id}")
    public String viewVehicle(@PathVariable int id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "vehicle-details";
        }
        return "redirect:/vehicles";
    }

    // Show vehicles by brand
    @GetMapping("/brand/{brand}")
    public String vehiclesByBrand(@PathVariable String brand, Model model) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByBrand(brand);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("filterType", "Brand: " + brand);
        return "vehicle-list";
    }
}