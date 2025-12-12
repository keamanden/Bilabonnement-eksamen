package com.example.demo.repository;

import com.example.demo.model.LeaseContractModel;

import java.time.LocalDate;
import java.util.List;

public interface LeaseContractRepository {

    void save(LeaseContractModel lease);

    // JOIN query: Lease + Customer + Vehicle
    List<LeaseContractModel> findCurrentLeases(LocalDate date);

    // SUM of all total_price
    double sumAllTotalPrices();

    // COUNT of all leases
    long countAllLeases();
}