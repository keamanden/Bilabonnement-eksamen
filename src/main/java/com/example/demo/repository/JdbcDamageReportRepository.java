package com.example.demo.repository;

import com.example.demo.model.DamageReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcDamageReportRepository implements DamageReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDamageReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private DamageReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        DamageReport d = new DamageReport();
        d.setDamageId(rs.getLong("damage_id"));
        d.setDamageDate(rs.getDate("damage_date").toLocalDate());
        d.setDescription(rs.getString("description"));
        d.setRepairCost(rs.getDouble("repair_cost"));
        d.setVin(rs.getString("vin"));
        d.setEmployeeId(rs.getLong("employee_id"));
        d.setLeaseId(rs.getLong("lease_id"));
        d.setKmSlut(rs.getInt("km_slut"));
        return d;
    }

    @Override
    public void save(DamageReport report) {
        if (report.getDamageId() == null) {
            // INSERT
            String sql = """
                INSERT INTO DamageReport
                (damage_date, description, repair_cost, vin, employee_id, lease_id, km_slut)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            jdbcTemplate.update(sql,
                    report.getDamageDate(),
                    report.getDescription(),
                    report.getRepairCost(),
                    report.getVin(),
                    report.getEmployeeId(),
                    report.getLeaseId(),
                    report.getKmSlut()
            );
        } else {
            // UPDATE
            String sql = """
                UPDATE DamageReport
                SET damage_date = ?, description = ?, repair_cost = ?, vin = ?,
                    employee_id = ?, lease_id = ?, km_slut = ?
                WHERE damage_id = ?
            """;
            jdbcTemplate.update(sql,
                    report.getDamageDate(),
                    report.getDescription(),
                    report.getRepairCost(),
                    report.getVin(),
                    report.getEmployeeId(),
                    report.getLeaseId(),
                    report.getKmSlut(),
                    report.getDamageId()
            );
        }
    }

    @Override
    public List<DamageReport> findAll() {
        String sql = "SELECT * FROM DamageReport";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public List<DamageReport> findByVin(String vin) {
        String sql = "SELECT * FROM DamageReport WHERE vin = ?";
        return jdbcTemplate.query(sql, this::mapRow, vin);
    }

    @Override
    public List<DamageReport> findByLeaseId(Long leaseId) {
        String sql = "SELECT * FROM DamageReport WHERE lease_id = ?";
        return jdbcTemplate.query(sql, this::mapRow, leaseId);
    }
}