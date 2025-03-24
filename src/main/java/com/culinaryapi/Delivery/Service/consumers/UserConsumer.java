package com.culinaryapi.Delivery.Service.consumers;

import com.culinaryapi.Delivery.Service.dtos.ActionType;
import com.culinaryapi.Delivery.Service.dtos.UserEventDto;
import com.culinaryapi.Delivery.Service.services.DeliverymanService;
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
public class UserConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);

    private final DeliverymanService deliverymanService;

    public UserConsumer(DeliverymanService deliverymanService) {
        this.deliverymanService = deliverymanService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${Culinary.broker.queue.deliverymanEventQueue.name}", durable = "true"),
            exchange = @Exchange(
                    value = "${Culinary.broker.exchange.deliverymanEvent}",
                    type = ExchangeTypes.DIRECT,
                    ignoreDeclarationExceptions = "true"
            ),
            key = "deliveryman.service.event"
    ))
    public void listenDeliverymanEvent(@Payload UserEventDto userEventDto) {
        LOGGER.info("Received Deliveryman Event: UserId={}, FullName={}",
                userEventDto.getUserId(),
                userEventDto.getFullName()
        );

        var deliveryman = userEventDto.convertToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())) {
            case CREATE:
            case UPDATE:
                LOGGER.info("Saving deliveryman with UserId={}", userEventDto.getUserId());
                deliverymanService.save(deliveryman);
                break;
            case DELETE:
                LOGGER.info("Delete action received, but no processing is defined for UserId={}", userEventDto.getUserId());
                break;
            default:
                LOGGER.warn("Unknown action type: {}", userEventDto.getActionType());
        }
    }
}
