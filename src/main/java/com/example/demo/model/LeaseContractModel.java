package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
 @Table(name = "leasecontract")
public class LeaseContractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lease_id")
    private long leaseId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "vin_id")
    private Vehicle vehicle;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "km_start")
    private int kmStart;

    @Transient
    private Long customerId;

    @Transient
    private String vinId;


    public LeaseContractModel() {
    }


    // getters and setters
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }


    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setLeaseId(long id) {
        this.leaseId = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setKmStart(int kmStart) {
        this.kmStart = kmStart;
    }

    public long getLeaseId() {
        return leaseId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getKmStart() {
        return kmStart;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getVinId() { return vinId; }
    public void setVinId(String vinId) { this.vinId = vinId; }

}
