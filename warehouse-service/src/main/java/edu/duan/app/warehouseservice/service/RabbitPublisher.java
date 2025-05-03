package edu.duan.app.warehouseservice.service;

import edu.duan.app.api.OrderErrorEvent;
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

    public void publishOrderErrorEvent(OrderErrorEvent orderErrorEvent) {
        rabbitTemplate.convertAndSend("lab2.topic", "orders.error.events", orderErrorEvent);
    }
}
