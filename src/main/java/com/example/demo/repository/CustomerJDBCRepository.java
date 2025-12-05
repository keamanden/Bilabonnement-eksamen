package com.example.demo.repository;
/*
import com.example.demo.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

@Repository
public class CustomerJDBCRepository {


    public Long saveCustomerAndReturnPK(LeaseRequest leaseRequest) {
        String sqlCustomer = "INSERT INTO customer_model (dob, city, country, driver_license_number, email, first_name, house_number, last_name, phone, postal_code, street) VALUES ( ?, ?, ?,?,?,?,?,?,?,?,?)";


        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test_bilabonnement", "root", "");
             PreparedStatement ps = conn.prepareStatement(sqlCustomer, Statement.RETURN_GENERATED_KEYS)) {


            ps.setDate(1, java.sql.Date.valueOf(leaseRequest.getCustomer().getdOB()));
            ps.setString(2, leaseRequest.getCustomer().getCity());
            ps.setString(3, leaseRequest.getCustomer().getCountry());
            ps.setString(4, leaseRequest.getCustomer().getDriverLicenseNumber());
            ps.setString(5, leaseRequest.getCustomer().getEmail());
            ps.setString(6, leaseRequest.getCustomer().getFirstName());
            ps.setString(7, leaseRequest.getCustomer().getHouseNumber());
            ps.setString(8, leaseRequest.getCustomer().getLastName());
            ps.setString(9, leaseRequest.getCustomer().getPhone());
            ps.setString(10, leaseRequest.getCustomer().getPostalCode());
            ps.setString(11, leaseRequest.getCustomer().getStreet());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving lease", e);
        }

        throw new RuntimeException("no key generated");
    }

    


}
*/