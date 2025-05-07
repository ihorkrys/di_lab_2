package edu.duan.app.ordersservice.service;

import edu.duan.app.api.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishOrderEvent(OrderEvent orderEvent) {
        rabbitTemplate.convertAndSend("lab2.topic", "order.events", orderEvent);
        System.out.println("Order Event Published");
    }
}
