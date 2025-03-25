package com.culinaryapi.Delivery.Service.dtos;

import com.culinaryapi.Delivery.Service.enums.OrderStatus;

import java.util.UUID;

public class UpdateOrderStatusDto {
    private UUID deliveryId;
    private String pickupCode;

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }
}
