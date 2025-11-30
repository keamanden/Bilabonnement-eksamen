package com.example.demo.repository;


import com.example.demo.DTO.LeaseRequest;
import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class LeaseJDBCRepository {

    public void saveLeaseJDBC(LeaseRequest leaseRequest, Long customerId, Long vinID) {

        String sqlLease = "INSERT INTO lease_model (end_date, km_start, start_date, total_price, customer_id, vei_id) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test_bilabonnement", "root", "keamanden");
             PreparedStatement ps = conn.prepareStatement(sqlLease)) {


            ps.setDate(1, java.sql.Date.valueOf(leaseRequest.getLease().getEndDate()));
            ps.setInt(2, leaseRequest.getLease().getKmStart());
            ps.setDate(3, java.sql.Date.valueOf(leaseRequest.getLease().getStartDate()));
            ps.setDouble(4, leaseRequest.getLease().getTotalPrice());
            ps.setLong(5, customerId);
            ps.setLong(6, vinID);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving lease", e);
        }
    }




    public LeaseJDBCRepository() throws SQLException {
    }


}
