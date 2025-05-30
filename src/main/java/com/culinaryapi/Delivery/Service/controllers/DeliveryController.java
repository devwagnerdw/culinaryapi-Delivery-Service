package com.culinaryapi.Delivery.Service.controllers;

import com.culinaryapi.Delivery.Service.dtos.AssignDeliveryDto;
import com.culinaryapi.Delivery.Service.dtos.UpdateOrderStatusDto;
import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Listar entregas disponíveis", description = "Retorna uma lista paginada de entregas disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entregas listadas com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/available")
    public ResponseEntity<Page<DeliveryModel>> getAvailableDeliveries(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return deliveryService.getAvailableDeliveries(pageable);
    }

    @Operation(summary = "Atribuir entrega ao entregador", description = "Atribui uma entrega específica a um entregador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega atribuída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping("/assign")
    public ResponseEntity<Object> assignDeliveryToDeliveryman(@RequestBody AssignDeliveryDto assignDeliveryDto) {
        return deliveryService.assignDeliveryToDeliveryman(assignDeliveryDto);
    }

    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido de entrega.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<Object> updateStatusOrder(@RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        return deliveryService.updateStatusOrder(updateOrderStatusDto);
    }
}
