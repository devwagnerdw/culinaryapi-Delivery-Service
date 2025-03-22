package com.culinaryapi.Delivery.Service.services.impl;


import com.culinaryapi.Delivery.Service.dtos.VehicleDto;
import com.culinaryapi.Delivery.Service.exception.NotFoundException;
import com.culinaryapi.Delivery.Service.mappers.DeliverymanMapper;
import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.culinaryapi.Delivery.Service.records.DeliverymanResponseDto;
import com.culinaryapi.Delivery.Service.repositories.DeliverymanRepository;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class DeliverymanServiceImpl implements DeliverymanService {

    private  final DeliverymanRepository deliverymanRepository;

    public DeliverymanServiceImpl(DeliverymanRepository deliverymanRepository) {
        this.deliverymanRepository = deliverymanRepository;
    }

    @Override
    public void save(DeliverymanModel deliverymanModel) {
        deliverymanRepository.save(deliverymanModel);

    }

    @Override
    public Optional<DeliverymanModel> findById(UUID userId) {
     return  deliverymanRepository.findById(userId);
    }

    @Override
    public ResponseEntity<DeliverymanResponseDto> registerVehicle(VehicleDto vehicleDto) {
        DeliverymanModel deliverymanModel = deliverymanRepository.findById(vehicleDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + vehicleDto.getUserId()));
        deliverymanModel.setVehicle(vehicleDto.getVehicle());
        deliverymanModel.setAvailable(true);
        deliverymanRepository.save(deliverymanModel);
        return ResponseEntity.status(HttpStatus.OK).body(DeliverymanMapper.toDto(deliverymanModel));
    }

}
