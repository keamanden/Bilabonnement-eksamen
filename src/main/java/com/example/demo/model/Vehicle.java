package com.example.demo.model;

import jakarta.persistence.*;

/**
JPA NOTE:
@Entity / @Table / @Column etc. are left here only as documentation.
We no longer use JPA; all persistence is done via JDBC (Jdbc*Repository).
 */

@Entity
@Table(name = "Vehicle")
public class Vehicle {

    @Id
    @Column(name = "registration_no", nullable = false, length = 30)
    private String registrationNo;

    @Column(name = "vin", unique = true, nullable = false, length = 30)
    private String vin;

    @Column(name = "brand", nullable = false, length = 30)
    private String brand;

    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @Column(name = "model_year", nullable = false)
    private int modelYear;

    // Constructors
    public Vehicle() {
    }

    public Vehicle(String registrationNo, String vin, String brand, String model, int modelYear) {
        this.registrationNo = registrationNo;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    // Getters and Setters
    public String getVin() {
        return vin;
    }

    public void setVin(String vinId) {
        this.vin = vinId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNo='" + registrationNo + '\'' +
                ", vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", modelYear=" + modelYear +
                '}';
    }
}