package com.example.demo.repository;

import com.example.demo.model.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcVehicleRepository implements VehicleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVehicleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Vehicle> vehicleRowMapper = (rs, rowNum) -> {
        Vehicle v = new Vehicle();
        v.setRegistrationNo(rs.getString("registration_no"));
        v.setVin(rs.getString("vin"));
        v.setBrand(rs.getString("brand"));
        v.setModel(rs.getString("model"));
        v.setModelYear(rs.getInt("model_year"));
        return v;
    };

    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT registration_no, vin, brand, model, model_year FROM Vehicle";
        return jdbcTemplate.query(sql, vehicleRowMapper);
    }

    @Override
    public List<Vehicle> findAllOrderedByVin() {
        String sql = "SELECT registration_no, vin, brand, model, model_year " +
                "FROM Vehicle ORDER BY vin";
        return jdbcTemplate.query(sql, vehicleRowMapper);
    }

    @Override
    public Optional<Vehicle> findById(String registrationNo) {
        String sql = "SELECT registration_no, vin, brand, model, model_year " +
                "FROM Vehicle WHERE registration_no = ?";
        List<Vehicle> result = jdbcTemplate.query(sql, vehicleRowMapper, registrationNo);
        return result.stream().findFirst();
    }

    @Override
    public Optional<Vehicle> findByRegistrationNo(String registrationNo) {
        return findById(registrationNo);
    }

    @Override
    public List<Vehicle> findByBrand(String brand) {
        String sql = "SELECT registration_no, vin, brand, model, model_year " +
                "FROM Vehicle WHERE brand = ?";
        return jdbcTemplate.query(sql, vehicleRowMapper, brand);
    }

    @Override
    public List<Vehicle> findByBrandAndModel(String brand, String model) {
        String sql = "SELECT registration_no, vin, brand, model, model_year " +
                "FROM Vehicle WHERE brand = ? AND model = ?";
        return jdbcTemplate.query(sql, vehicleRowMapper, brand, model);
    }

    @Override
    public List<Vehicle> findByModelYear(int modelYear) {
        String sql = "SELECT registration_no, vin, brand, model, model_year " +
                "FROM Vehicle WHERE model_year = ?";
        return jdbcTemplate.query(sql, vehicleRowMapper, modelYear);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        String countSql = "SELECT COUNT(*) FROM Vehicle WHERE registration_no = ?";
        Integer count = jdbcTemplate.queryForObject(
                countSql, Integer.class, vehicle.getRegistrationNo()
        );

        if (count != null && count > 0) {
            String updateSql = "UPDATE Vehicle " +
                    "SET vin = ?, brand = ?, model = ?, model_year = ? " +
                    "WHERE registration_no = ?";
            jdbcTemplate.update(updateSql,
                    vehicle.getVin(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getModelYear(),
                    vehicle.getRegistrationNo());
        } else {
            String insertSql = "INSERT INTO Vehicle " +
                    "(registration_no, vin, brand, model, model_year) " +
                    "VALUES (?,?,?,?,?)";
            jdbcTemplate.update(insertSql,
                    vehicle.getRegistrationNo(),
                    vehicle.getVin(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getModelYear());
        }
        return vehicle;
    }

    @Override
    public void deleteById(String registrationNo) {
        String sql = "DELETE FROM Vehicle WHERE registration_no = ?";
        jdbcTemplate.update(sql, registrationNo);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Vehicle";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        return (result != null) ? result : 0L;
    }
}