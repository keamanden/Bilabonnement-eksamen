package com.example.demo.controller;

import com.example.demo.DTO.LeaseRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.LeaseService;
import com.example.demo.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/lease")
public class LeaseController {


    private final LeaseRepository leaseRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final LeaseService leaseService;


    public LeaseController(LeaseRepository leaseRepository, CustomerRepository customerRepository, VehicleRepository vehicleRepository, VehicleService vehicleService, LeaseService leaseService) {
        this.leaseRepository = leaseRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.leaseService = leaseService;
    }


    @GetMapping
    public String showLeaseForm(Model model) {
        model.addAttribute("leaseRequest", new LeaseRequest());
        return "pages/lease";
    }


    // Create lease håndteres igennem leaseRequst da formen indeholder både data for lease og customer
    // som håndteres samtidig var det nødvendigt at lave en DTO for at undgå concurrency problemer.
    @PostMapping
    public String createLeaseAndAddToDB(@ModelAttribute LeaseRequest leaseRequest, Model model) {

        try {
            leaseService.createLease(leaseRequest);
            model.addAttribute("Success", true);
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("Success", false);
        } catch (Exception e) {
            model.addAttribute("Success", false);
            model.addAttribute("Error", "unexpected error");
        }
        model.addAttribute("leaseRequest", new LeaseRequest());
        return "pages/lease";
    }




}
