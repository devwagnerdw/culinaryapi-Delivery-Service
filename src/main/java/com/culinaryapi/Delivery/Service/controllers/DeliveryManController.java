package com.culinaryapi.Delivery.Service.controllers;


import com.culinaryapi.Delivery.Service.dtos.VehicleDto;
import com.culinaryapi.Delivery.Service.records.DeliverymanResponseDto;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/deliveryman")
public class DeliveryManController {

    private final DeliverymanService deliverymanService;

    public DeliveryManController(DeliverymanService deliverymanService) {
        this.deliverymanService = deliverymanService;
    }


    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping
    public ResponseEntity<DeliverymanResponseDto> registerVehicle(@RequestBody @Validated() VehicleDto vehicleDto){
        return deliverymanService.registerVehicle(vehicleDto);
    }

}