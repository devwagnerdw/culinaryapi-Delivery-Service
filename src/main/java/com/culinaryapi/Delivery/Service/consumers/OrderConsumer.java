package com.culinaryapi.Delivery.Service.consumers;

import com.culinaryapi.Delivery.Service.dtos.ActionType;
import com.culinaryapi.Delivery.Service.dtos.OrderEventDto;
import com.culinaryapi.Delivery.Service.services.DeliveryService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private final DeliveryService deliveryService;

    public OrderConsumer(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${Culinary.broker.queue.deliveryEventQueue.name}", durable = "true"),
            exchange = @Exchange(
                    value = "${Culinary.broker.exchange.orderServiceEventExchange}",
                    type = ExchangeTypes.DIRECT,
                    ignoreDeclarationExceptions = "true"
            ),
            key = "order.service.event"
    ))
    public void listenOrderEvent(@Payload OrderEventDto orderEventDto) {
        LOGGER.info("Received Order Event: OrderId={}, Status={}, FullName={}",
                orderEventDto.getOrderId(),
                orderEventDto.getOrderStatus(),
                orderEventDto.getFullName()
        );

        var delivery = orderEventDto.convertToDeliveryModel();

        switch (ActionType.valueOf(orderEventDto.getActionType())) {
            case CREATE:
            case UPDATE:
                LOGGER.info("Saving delivery for OrderId={}", orderEventDto.getOrderId());
                deliveryService.save(delivery);
                break;
            case DELETE:
                LOGGER.info("Delete action received, but no processing is defined for OrderId={}", orderEventDto.getOrderId());
                break;
            default:
                LOGGER.warn("Unknown action type: {}", orderEventDto.getActionType());
        }
    }
}
