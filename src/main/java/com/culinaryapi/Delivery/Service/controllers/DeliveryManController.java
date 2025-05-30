package com.culinaryapi.Delivery.Service.controllers;


import com.culinaryapi.Delivery.Service.dtos.VehicleDto;
import com.culinaryapi.Delivery.Service.records.DeliverymanResponseDto;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/deliveryman")
public class DeliveryManController {

    private final DeliverymanService deliverymanService;

    public DeliveryManController(DeliverymanService deliverymanService) {
        this.deliverymanService = deliverymanService;
    }


    @Operation(summary = "Registrar veículo do entregador", description = "Registra um novo veículo associado ao entregador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping
    public ResponseEntity<DeliverymanResponseDto> registerVehicle(@RequestBody @Validated() VehicleDto vehicleDto){
        return deliverymanService.registerVehicle(vehicleDto);
    }

}