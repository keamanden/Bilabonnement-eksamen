package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class VehicleJDBCRepository {


    public Long findVehicleIdByRegistration(String registrationNo) {
        String sql = "SELECT vei_id FROM vehicle WHERE registration_no = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test_bilabonnement", "root", "keamanden"
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, registrationNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("vei_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicle ID", e);
        }
        return null;
    }

}
