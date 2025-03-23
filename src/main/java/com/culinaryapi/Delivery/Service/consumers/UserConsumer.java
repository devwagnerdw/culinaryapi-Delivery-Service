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

@Component
public class UserConsumer {


    private  final DeliverymanService deliverymanService;

    public UserConsumer(DeliverymanService deliverymanService) {
        this.deliverymanService = deliverymanService;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${Culinary.broker.queue.deliverymanEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${Culinary.broker.exchange.deliverymanEvent}", type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "deliveryman.service.event") // Routing key

    )
    public void listenUserEvent(@Payload UserEventDto userEventDto){

       var deliveryman = userEventDto.convertToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())){
            case CREATE:
            case UPDATE:
                deliverymanService.save(deliveryman);
                break;
            case DELETE:
                break;
        }
    }
}
