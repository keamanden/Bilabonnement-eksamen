package com.example.demo.repository;

import com.example.demo.model.LeaseContractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;



public interface LeaseContractRepository extends JpaRepository<LeaseContractModel, Long> {

    List<LeaseContractModel> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}
