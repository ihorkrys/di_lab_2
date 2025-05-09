package edu.duan.app.usersservice.service;

import edu.duan.app.api.UserEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitPublisher {
    private RabbitTemplate rabbitTemplate;

    public void publishUserEvent(UserEvent userEvent) {
        rabbitTemplate.convertAndSend("lab2.topic", "user.events", userEvent);
    }
}
