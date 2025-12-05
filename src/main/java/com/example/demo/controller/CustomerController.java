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

//Siden customer gemmes via oprettelsen af et lease er customercontroller ikke brugt.
//Opfylder ikke SOLID men jf. kravspecifikationer giver seperat customer oprettelse ikke mening.
// Classen beholdes af symbolske årsager og hvis man ønskede at splitte oprettelsen senere kan
//Customer placeres her

public class CustomerController {


}
