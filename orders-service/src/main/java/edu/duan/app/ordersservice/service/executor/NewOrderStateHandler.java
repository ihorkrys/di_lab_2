package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.service.RabbitPublisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
@AllArgsConstructor
public class NewOrderStateHandler implements OrderStateHandler {
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Override
    public OrderState getOrderState() {
        return OrderState.NEW;
    }

    @Override
    @Transactional
    public OrderStateEntity handle(OrderEntity orderEntity) {
        orderEntity.setState(OrderStateEntity.NEW);
        rabbitPublisher.publishOrderEvent(buildOrderEvent(orderEntity));
        return orderEntity.getState();
    }
}
