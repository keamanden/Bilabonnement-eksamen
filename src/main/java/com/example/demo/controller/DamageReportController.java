package com.example.demo.controller;

import com.example.demo.model.DamageReport;
import com.example.demo.repository.DamageReportRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamageReportController {

    // Repositories til skader og biler
    private final DamageReportRepository damageReportRepository;
    private final VehicleRepository vehicleRepository;
    private final EmployeeRepository employeeRepository;

    private static final Logger log = LoggerFactory.getLogger(DamageReportController.class);

    // Dependency injection via constructor
    public DamageReportController(DamageReportRepository damageReportRepository,
                                  VehicleRepository vehicleRepository,
                                  EmployeeRepository employeeRepository) {
        this.damageReportRepository = damageReportRepository;
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
    }

    // Viser skade-registreringssiden med tomt DamageReport-objekt og liste af biler (VIN)
    @GetMapping("/damagereport")
    public String showDamageReportForm(Model model) {
        DamageReport damageReport = new DamageReport();
        // Defaults so hidden fields are actually posted (th:field uses object values)
        damageReport.setLeaseId(1L);
        damageReport.setKmSlut(0);
        damageReport.setRepairCost(0.00);
        model.addAttribute("damageReport", damageReport);
        model.addAttribute("vehicles", vehicleRepository.findAllOrderedByVin());
        model.addAttribute("employees", employeeRepository.findAll());
        return "pages/damagereport";
    }

    // Gemmer skade-rapport og sender bruger tilbage med succes/fejl parameter
    @PostMapping("/damage/save")
    public String saveDamageReport(@ModelAttribute("damageReport") DamageReport damageReport) {
        try {
            damageReportRepository.save(damageReport);
            return "redirect:/damagereport?success=true";
        } catch (Exception e) {
            log.error("Failed to save damage report", e);
            return "redirect:/damagereport?error=true";
        }
    }
}

