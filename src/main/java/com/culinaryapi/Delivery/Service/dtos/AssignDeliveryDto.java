package com.culinaryapi.Delivery.Service.dtos;

import java.util.UUID;

public record AssignDeliveryDto(UUID deliveryId, UUID deliverymanId) {
}
