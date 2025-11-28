package com.example.demo.controller;


import com.example.demo.model.CustomerModel;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;


    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/lease")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerModel());
        return "pages/lease";
    }

    @PostMapping("/lease")
    public String CreateCustomer(@ModelAttribute CustomerModel customerModel, Model model) {

        customerRepository.save(customerModel);
        model.addAttribute("customer", customerModel);
        return "customer";
    }


}
