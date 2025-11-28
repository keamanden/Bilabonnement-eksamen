package com.example.demo.repository;

import com.example.demo.model.LeaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.dgc.Lease;

public interface LeaseRepository extends JpaRepository<LeaseModel, Long> {


}
