package com.culinaryapi.Delivery.Service.controllers;

import com.culinaryapi.Delivery.Service.dtos.AssignDeliveryDto;
import com.culinaryapi.Delivery.Service.dtos.UpdateOrderStatusDto;
import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @GetMapping("/available")
    public ResponseEntity<Page<DeliveryModel>> getAvailableDeliveries(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return deliveryService.getAvailableDeliveries(pageable);
    }


    @PostMapping("/assign")
    public ResponseEntity<Object> assignDeliveryToDeliveryman(@RequestBody AssignDeliveryDto assignDeliveryDto) {
      return  deliveryService.assignDeliveryToDeliveryman(assignDeliveryDto);
    }

    @PostMapping()
    public  ResponseEntity<Object> updateStatusOrder(@RequestBody UpdateOrderStatusDto updateOrderStatusDto){
        return deliveryService.updateStatusOrder(updateOrderStatusDto);
    }
}
