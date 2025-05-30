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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliverymanRepository deliverymanRepository;

    @Mock
    private DeliveryEventPublisher deliveryEventPublisher;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_ShouldSaveDelivery() {
        DeliveryModel delivery = new DeliveryModel();
        deliveryService.save(delivery);
        verify(deliveryRepository, times(1)).save(delivery);
    }

    @Test
    void testGetAvailableDeliveries_ShouldReturnReadyDeliveries() {
        Pageable pageable = Pageable.unpaged();
        DeliveryModel delivery = new DeliveryModel();
        delivery.setOrderStatus(OrderStatus.READY_FOR_PICKUP);

        Page<DeliveryModel> page = new PageImpl<>(List.of(delivery));
        when(deliveryRepository.findByOrderStatus(OrderStatus.READY_FOR_PICKUP, pageable)).thenReturn(page);

        ResponseEntity<Page<DeliveryModel>> response = deliveryService.getAvailableDeliveries(pageable);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void testAssignDeliveryToDeliveryman_ShouldAssignSuccessfully() {
        UUID deliveryId = UUID.randomUUID();
        UUID deliverymanId = UUID.randomUUID();

        DeliveryModel deliveryModel = new DeliveryModel();
        deliveryModel.setOrderStatus(OrderStatus.READY_FOR_PICKUP);

        DeliverymanModel deliveryman = new DeliverymanModel();
        deliveryman.setAvailable(true);

        AssignDeliveryDto dto = new AssignDeliveryDto(deliveryId, deliverymanId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(deliveryModel));
        when(deliverymanRepository.findById(deliverymanId)).thenReturn(Optional.of(deliveryman));

        ResponseEntity<Object> response = deliveryService.assignDeliveryToDeliveryman(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Delivery was assigned successfully", response.getBody());

        verify(deliveryRepository).save(deliveryModel);
        verify(deliveryEventPublisher).publishOrderEvent(any(), eq(ActionType.UPDATE));
    }

    @Test
    void testAssignDeliveryToDeliveryman_DeliveryNotFound() {
        UUID deliveryId = UUID.randomUUID();
        UUID deliverymanId = UUID.randomUUID();
        AssignDeliveryDto dto = new AssignDeliveryDto(deliveryId, deliverymanId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> deliveryService.assignDeliveryToDeliveryman(dto));
    }

    @Test
    void testAssignDeliveryToDeliveryman_DeliverymanNotFound() {
        UUID deliveryId = UUID.randomUUID();
        UUID deliverymanId = UUID.randomUUID();

        DeliveryModel deliveryModel = new DeliveryModel();
        deliveryModel.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
        AssignDeliveryDto dto = new AssignDeliveryDto(deliveryId, deliverymanId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(deliveryModel));
        when(deliverymanRepository.findById(deliverymanId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> deliveryService.assignDeliveryToDeliveryman(dto));
    }

    @Test
    void testAssignDeliveryToDeliveryman_DeliveryNotReady() {
        UUID deliveryId = UUID.randomUUID();
        UUID deliverymanId = UUID.randomUUID();

        DeliveryModel deliveryModel = new DeliveryModel();
        deliveryModel.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

        DeliverymanModel deliveryman = new DeliverymanModel();
        deliveryman.setAvailable(true);

        AssignDeliveryDto dto = new AssignDeliveryDto(deliveryId, deliverymanId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(deliveryModel));
        when(deliverymanRepository.findById(deliverymanId)).thenReturn(Optional.of(deliveryman));

        assertThrows(BadRequestException.class, () -> deliveryService.assignDeliveryToDeliveryman(dto));
    }

    @Test
    void testAssignDeliveryToDeliveryman_DeliverymanUnavailable() {
        UUID deliveryId = UUID.randomUUID();
        UUID deliverymanId = UUID.randomUUID();

        DeliveryModel deliveryModel = new DeliveryModel();
        deliveryModel.setOrderStatus(OrderStatus.READY_FOR_PICKUP);

        DeliverymanModel deliveryman = new DeliverymanModel();
        deliveryman.setAvailable(false);

        AssignDeliveryDto dto = new AssignDeliveryDto(deliveryId, deliverymanId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(deliveryModel));
        when(deliverymanRepository.findById(deliverymanId)).thenReturn(Optional.of(deliveryman));

        assertThrows(BadRequestException.class, () -> deliveryService.assignDeliveryToDeliveryman(dto));
    }

    @Test
    void testUpdateStatusOrder_Successful() {
        UUID deliveryId = UUID.randomUUID();
        String phone = "999991234";

        DeliveryModel delivery = new DeliveryModel();
        delivery.setPhoneNumber(phone);
        delivery.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

        UpdateOrderStatusDto dto = new UpdateOrderStatusDto();
        dto.setDeliveryId(deliveryId);
        dto.setPickupCode("1234");

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));

        ResponseEntity<Object> response = deliveryService.updateStatusOrder(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("delivery completed successfully", response.getBody());

        verify(deliveryRepository).save(delivery);
        verify(deliveryEventPublisher).publishOrderEvent(any(), eq(ActionType.UPDATE));
    }

    @Test
    void testUpdateStatusOrder_InvalidCode() {
        UUID deliveryId = UUID.randomUUID();
        DeliveryModel delivery = new DeliveryModel();
        delivery.setPhoneNumber("999991234");

        UpdateOrderStatusDto dto = new UpdateOrderStatusDto();
        dto.setDeliveryId(deliveryId);
        dto.setPickupCode("1232");

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));

        assertThrows(BadRequestException.class, () -> deliveryService.updateStatusOrder(dto));
    }

    @Test
    void testUpdateStatusOrder_DeliveryNotFound() {
        UUID deliveryId = UUID.randomUUID();

        UpdateOrderStatusDto dto = new UpdateOrderStatusDto();
        dto.setDeliveryId(deliveryId);
        dto.setPickupCode("1234");

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> deliveryService.updateStatusOrder(dto));
    }
}
