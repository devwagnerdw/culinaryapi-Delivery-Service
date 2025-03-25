package com.culinaryapi.Delivery.Service.models;

import com.culinaryapi.Delivery.Service.dtos.DeliveryEventDto;
import com.culinaryapi.Delivery.Service.enums.OrderStatus;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_DELIVERY")
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
    private String address;
    @ManyToOne
    @JoinColumn(name = "deliveryman_id")
    private DeliverymanModel deliveryman;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliverymanModel getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(DeliverymanModel deliveryman) {
        this.deliveryman = deliveryman;
    }

    public DeliveryEventDto convertToDeliveryEventDto() {
        var deliveryEventDto= new DeliveryEventDto();
        BeanUtils.copyProperties(this, deliveryEventDto);
        return deliveryEventDto;

    }
}
