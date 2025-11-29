package com.example.demo.service;


import com.example.demo.DTO.LeaseRequest;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseModel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LeaseRepository;
import org.springframework.stereotype.Service;


@Service
public class LeaseService {

    private final CustomerRepository customerRepository;
    private final LeaseRepository leaseRepository;
    private final VehicleService vehicleService;

    public LeaseService(CustomerRepository customerRepository, LeaseRepository leaseRepository, VehicleService vehicleService) {
        this.customerRepository = customerRepository;
        this.leaseRepository = leaseRepository;
        this.vehicleService = vehicleService;
    }

    public void createLease(LeaseRequest leaseRequest) {

        CustomerModel customer = customerRepository.save(leaseRequest.getCustomer());

        leaseRequest.setVehicle(vehicleService.getVehicleByRegistrationNo(leaseRequest.getVehicle().getRegistrationNo()).
                orElseThrow(() -> new IllegalArgumentException("Vehicle not found")));


        //Data håndteres i leaserequest intil persistance
        LeaseModel lease = leaseRequest.getLease();

        lease.setCustomer(customer);
        lease.setVehicle(leaseRequest.getVehicle());

        leaseRepository.save(lease);
    }

}
