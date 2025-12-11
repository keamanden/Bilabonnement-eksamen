package com.example.demo;

import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseContractModel;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LeaseContractRepository;
import com.example.demo.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/* This is a unit test for test case TC01 */

// Tells spring to start the whole program in test mode
@SpringBootTest
// Tells spring to create a MockMvc instance to fake HTTP requests (GET and POST)
@AutoConfigureMockMvc
class LeaseContractHappyFlowTest {

    // Autowired creates objects and injects it into the test
    // mockMvc is like a fake browser that can send GET and POST requests to the controller methods in tests
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LeaseContractRepository leaseRepository;

    // The test method
    @Test
    void createLeaseContract_fullHappyFlow() throws Exception {

        // Preconditions customer and vehicle needs to exist in the database
        // Take the first customer and vehicle from data.sql/database
        CustomerModel customer = customerRepository.findAll().get(0);
        Vehicle vehicle = vehicleRepository.findAll().get(0);

        // Checks how many lease contracts in the database
        long leasesBefore = leaseRepository.count();

        // Simulates user choosing customer + vehicle then clicking "Hent data"
        MvcResult getResult = mockMvc.perform( // Fake browser to perform HTTP requests
                        get("/leaseContract") // Simulates a GET reguest to /leaseContract
                                .param("customerId", customer.getCustomerId().toString())
                                .param("registrationNo", vehicle.getRegistrationNo())
                )
                .andExpect(status().isOk()) // Checks HTTP status
                .andExpect(view().name("pages/leaseContract")) // Check correct Thymeleaf page from controller
                .andReturn();

        // Model contains selectedCustomer + selectedVehicle taken from getResult
        ModelMap model = getResult.getModelAndView().getModelMap();

        // Casts stored object from "selectedCustomer" to CustomerModel. Same for the other
        CustomerModel selectedCustomer =
                (CustomerModel) model.get("selectedCustomer");
        Vehicle selectedVehicle =
                (Vehicle) model.get("selectedVehicle");

        // Checks if selectedCustomer and selectedVehicle contains data. If null = fail
        assertNotNull(selectedCustomer, "selectedCustomer should be set after Hent data");
        assertNotNull(selectedVehicle, "selectedVehicle should be set after Hent data");

        // Checks if the selected data is the same as the database by comparing CustomerId and RegistrationNo
        assertEquals(customer.getCustomerId(), selectedCustomer.getCustomerId());
        assertEquals(vehicle.getRegistrationNo(), selectedVehicle.getRegistrationNo());

        /* Simulate filling the form and clicking "Opret lejekontrakt" */
        // Date data used
        String startDateStr = "2025-01-15";
        String endDateStr   = "2025-06-15";

        // Fake browser instance to perform HTTP requests
        mockMvc.perform(
                        post("/leaseContract") // Simulates Submitting POST form
                                .param("customerId", customer.getCustomerId().toString())
                                .param("registrationNo", vehicle.getRegistrationNo())
                                .param("startDate", startDateStr)
                                .param("endDate", endDateStr)
                                .param("kmStart", "10000")
                                .param("totalPrice", "4500")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("pages/leaseContractSuccess"));


        // Checks if lease contract was saved in the database if we have 1 more than before
        assertEquals(leasesBefore + 1, leaseRepository.count(),
                "There should be one more lease after creation");

        // Finds all existing lease contracts in the database and picks the one the test created for the assertEquals()
        List<LeaseContractModel> allLeases = leaseRepository.findAll();
        LeaseContractModel created = allLeases.get(allLeases.size() - 1);

        // Checks if the lease contract is linked to the right customer and vehicle by comparing
        assertEquals(customer.getCustomerId(), created.getCustomer().getCustomerId());
        assertEquals(vehicle.getRegistrationNo(), created.getVehicle().getRegistrationNo());

        // LocalData parsing and checks for correct data
        assertEquals(LocalDate.parse(startDateStr), created.getStartDate());
        assertEquals(LocalDate.parse(endDateStr),   created.getEndDate());
        assertEquals(10000, created.getKmStart());
        assertEquals(4500.0, created.getTotalPrice(), 0.00001);
    }
}