package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DamageReport")
public class DamageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_id")
    private Long damageId;

    @Column(name = "damage_date", nullable = false)
    private LocalDate damageDate;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "repair_cost", nullable = false)
    private double repairCost;

    @Column(name = "vin_id", nullable = false, length = 30)
    private String vinId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "lease_id", nullable = false)
    private Long leaseId;

    @Column(name = "km_slut", nullable = false)
    private int kmSlut;

    // Constructors
    public DamageReport() {
    }

    // Getters and Setters
    public Long getDamageId() {
        return damageId;
    }

    public void setDamageId(Long damageId) {
        this.damageId = damageId;
    }

    public LocalDate getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public String getVinId() {
        return vinId;
    }

    public void setVinId(String vinId) {
        this.vinId = vinId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public int getKmSlut() {
        return kmSlut;
    }

    public void setKmSlut(int kmSlut) {
        this.kmSlut = kmSlut;
    }
}

