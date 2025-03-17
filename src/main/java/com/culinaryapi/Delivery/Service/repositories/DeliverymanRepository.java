package com.culinaryapi.Delivery.Service.repositories;

import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliverymanRepository extends JpaRepository<DeliverymanModel,UUID> {
}
