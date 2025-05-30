package com.culinaryapi.Delivery.Service.publishers;


import com.culinaryapi.Delivery.Service.dtos.ActionType;
import com.culinaryapi.Delivery.Service.dtos.DeliveryEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeliveryEventPublisher {


    private final RabbitTemplate rabbitTemplate;

    public DeliveryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Value(value="${Culinary.broker.exchange.deliveryEventExchange}" )
    private String exchangeDeliveryEvent;

    public void publishOrderEvent(DeliveryEventDto deliveryEventDto, ActionType actionType) {
        deliveryEventDto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeDeliveryEvent, "delivery.service.event", deliveryEventDto);
    }

}
