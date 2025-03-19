package com.culinaryapi.Delivery.Service.services;




import com.culinaryapi.Delivery.Service.dtos.VehicleDto;
import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.culinaryapi.Delivery.Service.records.DeliverymanResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface DeliverymanService {

    void save(DeliverymanModel deliverymanModel);
    Optional<DeliverymanModel> findById(UUID userId);

    ResponseEntity<DeliverymanResponseDto> registerVehicle(VehicleDto vehicleDto);
}
