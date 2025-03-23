package com.culinaryapi.Delivery.Service.services.impl;

import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import com.culinaryapi.Delivery.Service.repositories.DeliveryRepository;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void save(DeliveryModel delivery) {
        deliveryRepository.save(delivery);

    }
}
