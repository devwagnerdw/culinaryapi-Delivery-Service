package com.culinaryapi.Delivery.Service.dtos;

import com.culinaryapi.Delivery.Service.enums.Vehicle;

import java.util.UUID;

public class VehicleDto {

    private UUID userId;
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
