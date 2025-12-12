package com.example.demo.controller;
import com.example.demo.model.LeaseContractModel;
import org.springframework.ui.Model;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.LeaseContractService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Controller class handles web requests and returns view names rendered by thymeleaf
@Controller
public class LeaseContractController {

    private final LeaseContractService leaseContractService;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    // Constructor for database access for customers and vehicles also logic for saving leases etc
    public LeaseContractController(LeaseContractService leaseContractService,
                                   CustomerRepository customerRepository,
                                   VehicleRepository vehicleRepository) {
        this.leaseContractService = leaseContractService;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }


    // method that shows the leaseContract page and handles GET reguests
    @GetMapping("/leaseContract")
    public String showCreateLeaseForm(
            // Query parameters to pass data to thymeleaf template
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "registrationNo", required = false) String registrationNo,
            Model model) {

        // Loads all customers and vehicles sorted for dropdown menu
        var customers = customerRepository.findAllOrderedByFirstName();
        var vehicles  = vehicleRepository.findAllOrderedByVin();

        // This makes the leaseform data as an object for the thymeleaf form
        LeaseContractModel leaseForm = new LeaseContractModel();
        if (customerId != null) leaseForm.setCustomerId(customerId);
        if (registrationNo != null) leaseForm.setRegistrationNo(registrationNo);

        // This finds the customer and vehicle data in the database
        CustomerModel selectedCustomer = (customerId != null)
                ? customerRepository.findById(customerId)
                : null;

        Vehicle selectedVehicle = (registrationNo != null)
                ? vehicleRepository.findByRegistrationNo(registrationNo).orElse(null)
                : null;


        // This puts all data to the model and returns the view input data for Thymeleaf
        model.addAttribute("customers", customers);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("leaseForm", leaseForm);
        model.addAttribute("selectedCustomer", selectedCustomer);
        model.addAttribute("selectedVehicle", selectedVehicle);

        return "pages/leaseContract";
    }

    // Method that handles "opret lejekontrakt" submit
    @PostMapping("/leaseContract")
    public String createLease(@ModelAttribute("leaseForm") LeaseContractModel leaseForm, Model model) {

        // Simple server-sided validation check required fields
        // Also used for the unit test named LeaseContractExceptionFlowTest
        boolean missingRequired =
                leaseForm.getCustomerId() == null ||
                        leaseForm.getRegistrationNo() == null ||
                        leaseForm.getStartDate() == null ||
                        leaseForm.getEndDate() == null ||
                        leaseForm.getKmStart() == 0 ||
                        leaseForm.getTotalPrice() == 0.0;

        // if one or more inputs fields are missing then missingRequired = true then this if statement runs otherwise skip
        if (missingRequired) {
            // puts error message in model
            model.addAttribute("errorMessage", "Påkrævede felter mangler");

            // Loads all customers and vehicles sorted for dropdown menu
            var customers = customerRepository.findAllOrderedByFirstName();
            var vehicles  = vehicleRepository.findAllOrderedByVin();

            // This puts all data to the model and returns the view input data for Thymeleaf
            model.addAttribute("customers", customers);
            model.addAttribute("vehicles", vehicles);
            model.addAttribute("leaseForm", leaseForm);

            // stay on the same page
            return "pages/leaseContract";
        }

        // Saves the lease contract if all inputs is filled
        // Find the full customer and vehicle entity using the IDs from the form
        CustomerModel customer = customerRepository.findById(leaseForm.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        Vehicle vehicle = vehicleRepository
                .findByRegistrationNo(leaseForm.getRegistrationNo())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));


        // Attaches customer and vehicle data to the lease entity
        leaseForm.setCustomer(customer);
        leaseForm.setVehicle(vehicle);

        // Saves the lease data to the database
        leaseContractService.save(leaseForm);

        // Redirects back to the same page
        return "pages/leaseContractSuccess";
    }


/*
//     Create lease håndteres igennem leaseRequst da formen indeholder både data for lease og customer
//     som håndteres samtidig var det nødvendigt at lave en DTO for at undgå concurrency problemer.

    @PostMapping
    public String createLeaseAndAddToDB(@ModelAttribute LeaseRequest leaseRequest, Model model) {

        try {
            leaseService.createAndSaveLease(leaseRequest);
            model.addAttribute("success", true);
            model.addAttribute("leaseRequest", new LeaseRequest());
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("success", false);
           Hvis der indtastet forkert reg nr. slettes alt data i formen ikke
            model.addAttribute("leaseRequest", leaseRequest);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "unexpected error");
        }
        return "pages/lease";
    }
*/

}
