package com.culinaryapi.Delivery.Service.services;




import com.culinaryapi.Delivery.Service.models.DeliverymanModel;

import java.util.Optional;
import java.util.UUID;

public interface DeliverymanService {

    void save(DeliverymanModel deliverymanModel);
    Optional<DeliverymanModel> findById(UUID userId);
}
