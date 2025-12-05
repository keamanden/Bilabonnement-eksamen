package com.example.demo.controller;

import com.example.demo.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentLeaseController {

    private final LeaseContractService leaseContractService;

    public CurrentLeaseController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    // Used to show current leases
    @GetMapping({"/current", "/currentleasingcontracts"})
    public String showCurrentLeases(Model model) {
        var currentLeases = leaseContractService.getCurrentLeases();
        model.addAttribute("currentLeases", currentLeases);
        model.addAttribute("currentLeasesTotalPrice", leaseContractService.getCurrentLeasesTotalPrice());
        model.addAttribute("currentLeasesCount", currentLeases.size());
        return "pages/currentleasingcontracts";
    }
}