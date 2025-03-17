package com.culinaryapi.Delivery.Service.services.impl;


import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.culinaryapi.Delivery.Service.repositories.DeliverymanRepository;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
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
}
