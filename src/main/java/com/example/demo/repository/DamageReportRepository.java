package com.example.demo.repository;

import com.example.demo.model.DamageReport;

import java.util.List;

public interface DamageReportRepository {

    void save(DamageReport damageReport);

    List<DamageReport> findAll();

    List<DamageReport> findByVin(String vin);

    List<DamageReport> findByLeaseId(Long leaseId);
}