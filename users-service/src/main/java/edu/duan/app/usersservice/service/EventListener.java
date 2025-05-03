package edu.duan.app.usersservice.service;

import edu.duan.app.api.OrderErrorEvent;
import edu.duan.app.api.OrderEvent;
import edu.duan.app.api.UserEvent;
import edu.duan.app.usersservice.data.UserEntity;
import edu.duan.app.usersservice.data.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EventListener {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @RabbitListener(queues = "order.queue")
    @Transactional
    public void handleOrder(OrderEvent event) {
        Optional<UserEntity> maybeUserEntity = usersRepository.findById(event.getUserId());
        switch (event.getOrderState()) {
            case NEW -> maybeUserEntity.ifPresentOrElse(
                    user -> {
                        rabbitPublisher.publishUserEvent(UserEvent.builder()
                                .withOrderId(event.getId())
                                        .withUserId(user.getId())
                                        .withExist(true)
                                .build());
                        System.out.println("Send email for" + event.getOrderState().name() + " order to user email:" + user.getLogin());
                    },
                    () -> rabbitPublisher.publishUserEvent(UserEvent.builder()
                            .withOrderId(event.getId())
                            .withExist(false)
                            .build())
            );
            default -> maybeUserEntity.ifPresent(
                    user -> System.out.println("Send email for" + event.getOrderState().name() + " order to user email:" + user.getLogin())
            );
        }
    }

}
