package com.example.demo.model;
import jakarta.persistence.*;

/**
JPA NOTE:
@Entity / @Table / @Column etc. are left here only as documentation.
We no longer use JPA; all persistence is done via JDBC (Jdbc*Repository).
 */

@Entity
@Table(name = "user_model")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeType;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}