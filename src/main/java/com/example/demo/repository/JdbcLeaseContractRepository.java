package com.example.demo.repository;

import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseContractModel;
import com.example.demo.model.Vehicle;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcLeaseContractRepository implements LeaseContractRepository {

    private final DataSource dataSource;

    public JdbcLeaseContractRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(LeaseContractModel lease) {
        String sql = """
                INSERT INTO Leasecontract
                  (customer_id, registration_no, start_date, end_date, km_start, total_price)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, lease.getCustomer().getCustomerId());
            ps.setString(2, lease.getVehicle().getRegistrationNo());
            ps.setDate(3, Date.valueOf(lease.getStartDate()));
            ps.setDate(4, Date.valueOf(lease.getEndDate()));
            ps.setInt(5, lease.getKmStart());
            ps.setDouble(6, lease.getTotalPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving lease", e);
        }
    }

    @Override
    public List<LeaseContractModel> findCurrentLeases(LocalDate date) {

        String sql = """
                SELECT 
                  l.lease_id, l.start_date, l.end_date, l.total_price, l.km_start,
                  c.customer_id, c.first_name, c.last_name, c.email, c.phone,
                  c.date_of_birth, c.driver_license_no, c.street, c.house_no,
                  c.postal_code, c.city, c.country,
                  v.registration_no, v.vin, v.brand, v.model, v.model_year
                FROM Leasecontract l
                JOIN customer c ON l.customer_id = c.customer_id
                JOIN Vehicle v   ON l.registration_no = v.registration_no
                WHERE l.start_date <= ? AND l.end_date >= ?
                """;

        List<LeaseContractModel> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Date sqlDate = Date.valueOf(date);
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapLeaseRowWithJoins(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading current leases", e);
        }

        return result;
    }

    @Override
    public long countAllLeases() {
        String sql = "SELECT COUNT(*) FROM Leasecontract";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;

        } catch (SQLException e) {
            throw new RuntimeException("Error counting leases", e);
        }
    }

    @Override
    public double sumAllTotalPrices() {
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM Leasecontract";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total lease price", e);
        }
    }

    private LeaseContractModel mapLeaseRowWithJoins(ResultSet rs) throws SQLException {
        LeaseContractModel lease = new LeaseContractModel();
        lease.setLeaseId(rs.getLong("lease_id"));
        lease.setStartDate(rs.getDate("start_date").toLocalDate());
        lease.setEndDate(rs.getDate("end_date").toLocalDate());
        lease.setTotalPrice(rs.getDouble("total_price"));
        lease.setKmStart(rs.getInt("km_start"));

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

        Vehicle v = new Vehicle();
        v.setRegistrationNo(rs.getString("registration_no"));
        v.setVin(rs.getString("vin"));
        v.setBrand(rs.getString("brand"));
        v.setModel(rs.getString("model"));
        v.setModelYear(rs.getInt("model_year"));

        lease.setCustomer(c);
        lease.setVehicle(v);

        return lease;
    }
}