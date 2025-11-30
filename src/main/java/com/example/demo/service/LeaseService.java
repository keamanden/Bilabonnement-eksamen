package com.example.demo.service;


import com.example.demo.DTO.LeaseRequest;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseModel;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;


@Service
public class LeaseService {

    private final CustomerRepository customerRepository;
    private final LeaseRepository leaseRepository;
    private final VehicleService vehicleService;
    private final CustomerJDBCRepository customerJDBCRepository;
    private final VehicleJDBCRepository vehicleJDBCRepository;
    private final LeaseJDBCRepository leaseJDBCRepository;

    public LeaseService(CustomerRepository customerRepository, LeaseRepository leaseRepository, VehicleService vehicleService, CustomerJDBCRepository customerJDBCRepository, VehicleJDBCRepository vehicleJDBCRepository, LeaseJDBCRepository leaseJDBCRepository) {
        this.customerRepository = customerRepository;
        this.leaseRepository = leaseRepository;
        this.vehicleService = vehicleService;
        this.customerJDBCRepository = customerJDBCRepository;
        this.vehicleJDBCRepository = vehicleJDBCRepository;
        this.leaseJDBCRepository = leaseJDBCRepository;
    }

    public void createAndSaveLease(LeaseRequest leaseRequest) {

        CustomerModel customer = customerRepository.save(leaseRequest.getCustomer());

        leaseRequest.setVehicle(vehicleService.getVehicleByRegistrationNo(leaseRequest.getVehicle().getRegistrationNo()).
                orElseThrow(() -> new IllegalArgumentException("Vehicle not found")));


        //Data håndteres i leaserequest intil persistance
        LeaseModel lease = leaseRequest.getLease();

        lease.setCustomer(customer);
        lease.setVehicle(leaseRequest.getVehicle());

        leaseRepository.save(lease);
    }

    public void createAndSaveLeaseJDBC(LeaseRequest leaseRequest) {

        Long customerId = customerJDBCRepository.saveCustomerAndReturnPK(leaseRequest);

        Long vehicleId = vehicleJDBCRepository.findVehicleIdByRegistration(leaseRequest.getVehicle().getRegistrationNo());

        try {
            leaseJDBCRepository.saveLeaseJDBC(leaseRequest, customerId, vehicleId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
