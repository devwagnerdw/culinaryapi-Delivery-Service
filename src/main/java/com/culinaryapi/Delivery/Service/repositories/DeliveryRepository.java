package com.culinaryapi.Delivery.Service.repositories;

import com.culinaryapi.Delivery.Service.enums.OrderStatus;
import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryModel, UUID> {
    Page<DeliveryModel> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

}
