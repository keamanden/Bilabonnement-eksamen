package com.example.demo.controller;

import com.example.demo.model.DamageReport;
import com.example.demo.repository.DamageReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamageReportController {

    private final DamageReportRepository damageReportRepository;

    public DamageReportController(DamageReportRepository damageReportRepository) {
        this.damageReportRepository = damageReportRepository;
    }

    @GetMapping("/damagereport")
    public String showDamageReportForm(Model model) {
        DamageReport damageReport = new DamageReport();
        model.addAttribute("damageReport", damageReport);
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

