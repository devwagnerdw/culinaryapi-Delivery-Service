package com.culinaryapi.Delivery.Service.repositories;

import com.culinaryapi.Delivery.Service.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
