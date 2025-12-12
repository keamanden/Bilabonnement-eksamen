package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(rs.getLong("employee_id"));
        e.setUsername(rs.getString("username"));
        e.setPassword(rs.getString("password"));
        e.setName(rs.getString("name"));
        e.setRole(rs.getString("role"));
        return e;
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT employee_id, username, password, name, role FROM employee ORDER BY name";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Employee findById(Long id) {
        String sql = "SELECT employee_id, username, password, name, role FROM employee WHERE employee_id = ?";
        List<Employee> result = jdbcTemplate.query(sql, this::mapRow, id);
        return result.isEmpty() ? null : result.get(0);
    }
}
