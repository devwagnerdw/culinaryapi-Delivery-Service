package com.culinaryapi.Delivery.Service.models;

import com.culinaryapi.Delivery.Service.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

public class DeliveryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID orderId;
    private UUID userId;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private String fullName;
    private String phoneNumber;
    private String actionType;
    private String address;
    @ManyToOne
    @JoinColumn(name = "deliveryman_id")
    private DeliverymanModel deliveryman;



}
