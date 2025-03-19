package com.culinaryapi.Delivery.Service.mappers;

import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.culinaryapi.Delivery.Service.records.DeliverymanResponseDto;

public class DeliverymanMapper {
    public static DeliverymanResponseDto toDto(DeliverymanModel deliveryman) {
        return new DeliverymanResponseDto(
                deliveryman.getUserId(),
                deliveryman.getEmail(),
                deliveryman.getFullName(),
                deliveryman.getPhoneNumber(),
                deliveryman.getUserStatus(),
                deliveryman.getVehicle(),
                deliveryman.isAvailable()
        );
    }
}