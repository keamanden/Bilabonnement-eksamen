package com.example.demo.service;

import com.example.demo.model.LeaseContractModel;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class LeaseContractService {

    private final CustomerRepository customerRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final VehicleService vehicleService;

    //private final CustomerJDBCRepository customerJDBCRepository;
    //private final VehicleJDBCRepository vehicleJDBCRepository;
    //private final LeaseJDBCRepository leaseJDBCRepository;

    public LeaseContractService(CustomerRepository customerRepository, LeaseContractRepository leaseContractRepository, VehicleService vehicleService) {
        this.customerRepository = customerRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.vehicleService = vehicleService;

        //this.customerJDBCRepository = customerJDBCRepository;
        //this.vehicleJDBCRepository = vehicleJDBCRepository;
        //this.leaseJDBCRepository = leaseJDBCRepository;
    }

    // Retrieves a list of leaseContracts that are currently active
    public List<LeaseContractModel> getCurrentLeases() {
        LocalDate today = LocalDate.now();
        return leaseContractRepository.findCurrentLeases(today);
    }

    // Calculates the total price of all leases in the database
    public double getAllLeasesTotalPrice() {
        return leaseContractRepository.sumAllTotalPrices();
    }

    // Counts all leases in the database
    public long getAllLeasesCount() {
        return leaseContractRepository.countAllLeases();
    }

    // Calculates the total price of all currently active lease contracts
    public double getCurrentLeasesTotalPrice() {
        return getCurrentLeases().stream()
                .mapToDouble(LeaseContractModel::getTotalPrice)
                .sum();
    }

    // Saves a new leaseContract into the database
    public void save(LeaseContractModel lease) {
        leaseContractRepository.save(lease);
    }

}