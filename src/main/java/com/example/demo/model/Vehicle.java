package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vin_id")
    private String vinId;

    @Column(name = "registration_no", unique = true, nullable = false, length = 10)
    private String registrationNo;

    @Column(name = "brand", nullable = false, length = 30)
    private String brand;

    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @Column(name = "model_year", nullable = false)
    private int modelYear;

    // Constructors
    public Vehicle() {
    }

    public Vehicle(String vinId, String registrationNo, String brand, String model, int modelYear) {
        this.vinId = vinId;
        this.registrationNo = registrationNo;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    // Getters and Setters
    public String getVinId() {
        return vinId;
    }

    public void setVinId(String vinId) {
        this.vinId = vinId;
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
                "vinId=" + vinId +
                ", registrationNo='" + registrationNo + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", modelYear=" + modelYear +
                '}';
    }
}