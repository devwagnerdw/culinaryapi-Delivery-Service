package com.culinaryapi.Delivery.Service.dtos;


import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEventDto {

    private UUID userId;
    private String email;
    private String fullName;
    private String userStatus;
    private String phoneNumber;
    private String actionType;
    private String vehicle;
    private boolean available;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public DeliverymanModel convertToUserModel(){
        var deliveryman = new DeliverymanModel();
        BeanUtils.copyProperties(this, deliveryman);
        return deliveryman;
    }
}



