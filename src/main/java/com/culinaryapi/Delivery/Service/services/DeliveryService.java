package com.culinaryapi.Delivery.Service.services;

import com.culinaryapi.Delivery.Service.dtos.AssignDeliveryDto;
import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DeliveryService {

    void save(DeliveryModel delivery);

    ResponseEntity<Page<DeliveryModel>> getAvailableDeliveries(Pageable pageable);

    ResponseEntity<Object> assignDeliveryToDeliveryman(AssignDeliveryDto assignDeliveryDto);
}
