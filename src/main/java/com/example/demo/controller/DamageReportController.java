package com.example.demo.controller;

import com.example.demo.model.DamageReport;
import com.example.demo.repository.DamageReportRepository;
import com.example.demo.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamageReportController {

    private final DamageReportRepository damageReportRepository;
    private final VehicleService vehicleService;

    public DamageReportController(DamageReportRepository damageReportRepository, VehicleService vehicleService) {
        this.damageReportRepository = damageReportRepository;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/damagereport")
    public String showDamageReportForm(Model model) {
        DamageReport damageReport = new DamageReport();
        model.addAttribute("damageReport", damageReport);
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "pages/damagereport";
    }

    @PostMapping("/damage/save")
    public String saveDamageReport(@ModelAttribute("damageReport") DamageReport damageReport) {
        try {
            damageReportRepository.save(damageReport);
            return "redirect:/damagereport?success=true";
        } catch (Exception e) {
            return "redirect:/damagereport?error=true";
        }
    }
}

