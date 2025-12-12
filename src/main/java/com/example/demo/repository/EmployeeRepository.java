package com.example.demo.repository;

import com.example.demo.model.Employee;
import java.util.List;

public interface EmployeeRepository {

    // Used by DamageReportController
    List<Employee> findAll();

    // Useful for future lookups
    Employee findById(Long id);
}
