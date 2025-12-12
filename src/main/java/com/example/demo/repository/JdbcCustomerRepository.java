package com.example.demo.repository;

import com.example.demo.model.CustomerModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final DataSource dataSource;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<CustomerModel> findAllOrderedByFirstName() {
        String sql = "SELECT * FROM customer ORDER BY first_name";
        List<CustomerModel> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading customers", e);
        }

        return result;
    }

    @Override
    public CustomerModel findById(Long id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading customer", e);
        }
    }

    private CustomerModel mapRow(ResultSet rs) throws SQLException {
        CustomerModel c = new CustomerModel();
        c.setCustomerId(rs.getLong("customer_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setdOB(rs.getDate("date_of_birth").toLocalDate());
        c.setDriverLicenseNumber(rs.getString("driver_license_no"));
        c.setStreet(rs.getString("street"));
        c.setHouseNumber(rs.getString("house_no"));
        c.setPostalCode(rs.getString("postal_code"));
        c.setCity(rs.getString("city"));
        c.setCountry(rs.getString("country"));
        return c;
    }
}