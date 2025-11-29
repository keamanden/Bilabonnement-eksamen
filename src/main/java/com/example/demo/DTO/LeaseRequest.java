package com.example.demo.DTO;

import com.example.demo.model.CustomerModel;
import com.example.demo.model.LeaseModel;
import com.example.demo.model.Vehicle;


public class LeaseRequest {

    private CustomerModel customer;
    private LeaseModel lease;
    private Vehicle vehicle;

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setLease(LeaseModel lease) {
        this.lease = lease;
    }

    public LeaseModel getLease() {
        return lease;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
