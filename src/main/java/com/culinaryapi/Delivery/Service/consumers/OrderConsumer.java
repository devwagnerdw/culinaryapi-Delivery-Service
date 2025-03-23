package com.culinaryapi.Delivery.Service.consumers;

import com.culinaryapi.Delivery.Service.dtos.ActionType;
import com.culinaryapi.Delivery.Service.dtos.OrderEventDto;
import com.culinaryapi.Delivery.Service.dtos.UserEventDto;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private  final DeliveryService deliveryService;

    public OrderConsumer(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${Culinary.broker.queue.deliveryEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${Culinary.broker.exchange.orderServiceEventExchange}", type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "order.service.event") // Routing key


    )

    public void listenUserEvent(@Payload OrderEventDto orderEventDto){

        System.out.println(orderEventDto.getOrderId());
        System.out.println(orderEventDto.getOrderStatus());
        System.out.println(orderEventDto.getFullName());


        var delivery = orderEventDto.convertToDeliveryModel();

        switch (ActionType.valueOf(orderEventDto.getActionType())){
            case CREATE:
            case UPDATE:
                deliveryService.save(delivery);
                break;
            case DELETE:
                break;
        }
    }
}
