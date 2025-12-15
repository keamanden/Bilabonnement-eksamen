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

// Tells spring this is a repository (data access class)
@Repository
// Implements methods defined in the interface
public class JdbcLeaseContractRepository implements LeaseContractRepository {

    // Used to obtain DB connections (usually via connection pool)
    private final DataSource dataSource;

    public JdbcLeaseContractRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Saves a lease object in the database
    @Override
    public void save(LeaseContractModel lease) {
        // SQL INSERT with placeholders (?)
        String sql = """
                INSERT INTO Leasecontract
                  (customer_id, registration_no, start_date, end_date, km_start, total_price)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        // Closes connection (conn) and preparedStatement (ps) automatically
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Sets parameters for the prepared statement 1 to 6 so it matches the ? positions
            ps.setLong(1, lease.getCustomer().getCustomerId());
            ps.setString(2, lease.getVehicle().getRegistrationNo());
            ps.setDate(3, Date.valueOf(lease.getStartDate())); // Converts LocalDate to java.sql.Date
            ps.setDate(4, Date.valueOf(lease.getEndDate())); // Converts LocalDate to java.sql.Date
            ps.setInt(5, lease.getKmStart());
            ps.setDouble(6, lease.getTotalPrice());

            // Runs INSERT (executeUpdate)
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving lease", e);
        }
    }

    // Finds all current leases where date is between start and end
    @Override
    public List<LeaseContractModel> findCurrentLeases(LocalDate date) {

        // SELECT with a JOIN so we combine lease + customer + vehicle  data in one query
        // WHERE active contract between the dates
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

        // Saves all the leases we find in the list
        List<LeaseContractModel> result = new ArrayList<>();

        // Closes connection (conn) and preparedStatement (ps) automatically
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // LocalDate > java.sql.Date
            Date sqlDate = Date.valueOf(date);
            // Adds date in placeholders
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);

            // executeQuery used for SELECT and returns ResultSet
            try (ResultSet rs = ps.executeQuery()) {
                // Loops through the ResultSet rows
                while (rs.next()) {
                    // Maps one row of data to LeaseContractModel with customer + vehicle
                    result.add(mapLeaseRowWithJoins(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading current leases", e);
        }

        return result; // returns empty list if no data
    }

    // Counts the amount of all existing leases in the database
    @Override
    public long countAllLeases() {
        // Returns a row with a number
        String sql = "SELECT COUNT(*) FROM Leasecontract";

        // Auto close for conn, ps, rs
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // ResultSet starts before first row next() goes to first row
            if (rs.next()) {
                return rs.getLong(1); // first column holds count data
            }
            return 0L;

        } catch (SQLException e) {
            throw new RuntimeException("Error counting leases", e);
        }
    }

    // Combines total price for all contracts together
    @Override
    public double sumAllTotalPrices() {
        // SUM teurns NULL where there are no rows COALESCE turn NULL into 0
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM Leasecontract";

        // Auto close for conn, ps, rs
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // ResultSet starts before first row next() goes to first row
            if (rs.next()) {
                return rs.getDouble(1); // first column holds total price data
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total lease price", e);
        }
    }

    // Maps a row from sql-results with JOINs to a LeaseContractModel object
    private LeaseContractModel mapLeaseRowWithJoins(ResultSet rs) throws SQLException {
        // Creates a LeaseContractModel and fills it with data from the current ResultSet row
        LeaseContractModel lease = new LeaseContractModel();
        lease.setLeaseId(rs.getLong("lease_id"));
        lease.setStartDate(rs.getDate("start_date").toLocalDate());
        lease.setEndDate(rs.getDate("end_date").toLocalDate());
        lease.setTotalPrice(rs.getDouble("total_price"));
        lease.setKmStart(rs.getInt("km_start"));

        // Creates a CustomerModel and fills it with data from the database
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

        // Creates a Vehicle object and fills it with data from the database
        Vehicle v = new Vehicle();
        v.setRegistrationNo(rs.getString("registration_no"));
        v.setVin(rs.getString("vin"));
        v.setBrand(rs.getString("brand"));
        v.setModel(rs.getString("model"));
        v.setModelYear(rs.getInt("model_year"));

        // Attach the Customer and Vehicle objects to the lease
        lease.setCustomer(c);
        lease.setVehicle(v);

        return lease; // returns the finished object
    }
}