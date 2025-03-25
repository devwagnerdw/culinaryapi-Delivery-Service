package com.culinaryapi.Delivery.Service.services.impl;

import com.culinaryapi.Delivery.Service.dtos.ActionType;
import com.culinaryapi.Delivery.Service.dtos.AssignDeliveryDto;
import com.culinaryapi.Delivery.Service.dtos.UpdateOrderStatusDto;
import com.culinaryapi.Delivery.Service.enums.OrderStatus;
import com.culinaryapi.Delivery.Service.exception.BadRequestException;
import com.culinaryapi.Delivery.Service.exception.NotFoundException;
import com.culinaryapi.Delivery.Service.models.DeliveryModel;
import com.culinaryapi.Delivery.Service.models.DeliverymanModel;
import com.culinaryapi.Delivery.Service.publishers.DeliveryEventPublisher;
import com.culinaryapi.Delivery.Service.repositories.DeliveryRepository;
import com.culinaryapi.Delivery.Service.repositories.DeliverymanRepository;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliverymanRepository deliverymanRepository;
    private final DeliveryEventPublisher deliveryEventPublisher;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliverymanRepository deliverymanRepository, DeliveryEventPublisher deliveryEventPublisher) {
        this.deliveryRepository = deliveryRepository;
        this.deliverymanRepository = deliverymanRepository;
        this.deliveryEventPublisher = deliveryEventPublisher;
    }

    @Override
    public void save(DeliveryModel delivery) {
        deliveryRepository.save(delivery);

    }

    @Override
    public ResponseEntity<Page<DeliveryModel>> getAvailableDeliveries(Pageable pageable) {
        Page<DeliveryModel> deliveries = deliveryRepository.findByOrderStatus(OrderStatus.READY_FOR_PICKUP, pageable);
        return ResponseEntity.ok(deliveries);
    }

    @Override
    public ResponseEntity<Object> assignDeliveryToDeliveryman(AssignDeliveryDto assignDeliveryDto) {

        DeliveryModel deliveryModel = deliveryRepository.findById(assignDeliveryDto.deliveryId())
                .orElseThrow(()-> new NotFoundException("Delivery not found: " + assignDeliveryDto.deliveryId() ));

        DeliverymanModel deliverymanModel = deliverymanRepository.findById(assignDeliveryDto.deliverymanId())
                        .orElseThrow(()-> new NotFoundException("DeliveryMan not found: " + assignDeliveryDto.deliverymanId()));

        if (!deliveryModel.getOrderStatus().equals(OrderStatus.READY_FOR_PICKUP)){
            throw new BadRequestException("The delivery is not ready for pickup.");
        }

        if (!deliverymanModel.isAvailable()){
             throw new BadRequestException("The deliveryman is not available.");
        }

        deliveryModel.setDeliveryman(deliverymanModel);
        deliveryModel.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

        deliveryRepository.save(deliveryModel);

        deliveryEventPublisher.publishOrderEvent(deliveryModel.convertToDeliveryEventDto(), ActionType.UPDATE);

       return ResponseEntity.status(HttpStatus.OK).body("Delivery was assigned successfully");
    }

    @Override
    public ResponseEntity<Object> updateStatusOrder(UpdateOrderStatusDto updateOrderStatusDto) {

        DeliveryModel deliveryModel = deliveryRepository.findById(updateOrderStatusDto.getDeliveryId())
                .orElseThrow(()-> new NotFoundException("Delivery not found: " + updateOrderStatusDto.getDeliveryId()));


        String phoneNumber = deliveryModel.getPhoneNumber();
        String lastFourDigits = phoneNumber.substring(phoneNumber.length() - 4);
        if (!lastFourDigits.equals(updateOrderStatusDto.getPickupCode())){
            throw new BadRequestException("the withdrawal code is invalid");
        }


        deliveryModel.setOrderStatus(OrderStatus.DELIVERED);
        deliveryRepository.save(deliveryModel);

        deliveryEventPublisher.publishOrderEvent(deliveryModel.convertToDeliveryEventDto(), ActionType.UPDATE);

        return ResponseEntity.status(HttpStatus.OK).body("delivery completed successfully");
    }

}
