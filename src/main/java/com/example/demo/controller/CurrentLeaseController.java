package com.example.demo.controller;

import com.example.demo.service.LeaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentLeaseController {

    private final LeaseService leaseService;

    public CurrentLeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    // Used to show current leases
    @GetMapping("/current")
    public String showCurrentLeases(Model model) {
        var currentLeases = leaseService.getCurrentLeases();
        model.addAttribute("currentLeases", currentLeases);
        model.addAttribute("currentLeasesTotalPrice", leaseService.getCurrentLeasesTotalPrice());
        model.addAttribute("currentLeasesCount", currentLeases.size());
        return "pages/currentleasingcontracts";
    }
}

