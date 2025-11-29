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

    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {

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
            model.addAttribute("success", true);
            model.addAttribute("leaseRequest", new LeaseRequest());
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("success", false);
            //Hvis der indtastet forkert reg nr. slettes alt data i formen ikke
            model.addAttribute("leaseRequest", leaseRequest);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("error", "unexpected error");
        }

        return "pages/lease";
    }




}
