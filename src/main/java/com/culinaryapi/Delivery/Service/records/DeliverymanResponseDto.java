package com.culinaryapi.Delivery.Service.records;

import com.culinaryapi.Delivery.Service.enums.Vehicle;

import java.util.UUID;

public record DeliverymanResponseDto(
        UUID userId,
        String email,
        String fullName,
        String phoneNumber,
        String userStatus,
        Vehicle vehicle,
        boolean available
) {}