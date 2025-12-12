package com.example.demo;

import com.example.demo.repository.LeaseContractRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/* This is a unit test for test case TC02 */

// Tells spring to start the whole program in test mode
@SpringBootTest
// Tells spring to create a MockMvc instance to fake HTTP requests (GET and POST)
@AutoConfigureMockMvc
class LeaseContractExceptionFlowTest {

    // Autowired creates objects and injects it into the test
    // mockMvc is like a fake browser that can send GET and POST requests to the controller methods in tests
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeaseContractRepository LeaseContractRepository;

    // The test method
    @Test
    void createLease_withMissingRequiredFields_showsErrorAndDoesNotSave() throws Exception {

        // Checks how many lease contracts exist in database and stores count in leasesBefore
        long leasesBefore = LeaseContractRepository.countAllLeases();

        /* simulate clicking "Opret lejekontrakt" with empty fields */

        // Fake browser instance to perform HTTP requests
        MvcResult result = mockMvc.perform(
                        post("/leaseContract") // Simulates Submitting POST form
                                // customerId and registrationNo is empty
                                // Data used for the test
                                .param("startDate", "")
                                .param("endDate", "2025-06-15")
                                .param("kmStart", "10000")
                                .param("totalPrice", "4500.0")
                )
                .andExpect(status().isOk()) // Checks HTTP status
                .andExpect(view().name("pages/leaseContract")) // Stays on same page
                .andReturn();

        // Checks if a new lease contract was saved
        long leasesAfter = LeaseContractRepository.countAllLeases();
        assertEquals(leasesBefore, leasesAfter, // Expect leasesBefore == leasesAfter or else it fails
                "No new lease should be saved when required fields are missing");

        // Checks if error message is in the model if invalid inputs
        ModelMap model = result.getModelAndView().getModelMap();
        assertTrue(model.containsAttribute("errorMessage"),
                "Model should contain an errorMessage attribute");

        // Gets the error message test from the model
        assertEquals("Påkrævede felter mangler",
                model.get("errorMessage"),
                "Error message should indicate missing required fields");
    }
}