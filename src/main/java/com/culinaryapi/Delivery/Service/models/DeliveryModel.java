package com.culinaryapi.Delivery.Service.models;

import jakarta.persistence.*;

import java.util.UUID;

public class DeliveryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID orderId;
    private String address;

    @ManyToOne
    @JoinColumn(name = "deliveryman_id")
    private DeliverymanModel deliveryman;

    private String status;

}
