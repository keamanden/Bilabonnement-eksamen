package com.example.demo.repository;

import com.example.demo.model.CustomerModel;
import java.util.List;

public interface CustomerRepository {

    // For dropdown in leaseContract page
    List<CustomerModel> findAllOrderedByFirstName();

    // For loading a specific customer in controller
    CustomerModel findById(Long id);
}