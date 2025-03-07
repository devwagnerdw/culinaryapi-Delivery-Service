package com.culinaryapi.Delivery.Service.models;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "TB_DELIVERIES")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID deliveryId;

    @Column(nullable = false, length = 100)
    private String deliveryman;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String address;

}
