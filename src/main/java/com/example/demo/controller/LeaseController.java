package com.example.demo.controller;

import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseModel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LeaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LeaseController {


    private final LeaseRepository leaseRepository;
    private final CustomerRepository customerRepository;



    public LeaseController(LeaseRepository leaseRepository, CustomerRepository customerRepository) {
        this.leaseRepository = leaseRepository;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/lease")
    public String lease() {
        return "pages/lease";
    }

    @PostMapping("/lease")
    public String CreateLeaseAndAddToDB(@ModelAttribute LeaseModel lease, @RequestParam Long customerId, Model model) {

        CustomerModel customer = customerRepository.findById(customerId).orElseThrow(() ->
                new IllegalArgumentException("Customer not found"));

        lease.setCustomer(customer);
    
        try {


            leaseRepository.save(lease);
            model.addAttribute("lease", lease);
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("success", false);
        }
        return "pages/lease";
    }




}
