package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.service.RabbitPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
@RequiredArgsConstructor
public class CanceledOrderStateHandler implements OrderStateHandler {
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Override
    public OrderState getOrderState() {
        return OrderState.CANCELLED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        orderEntity.setState(OrderStateEntity.CANCELLED);
        rabbitPublisher.publishOrderEvent(buildOrderEvent(orderEntity));
        return orderEntity.getState();
    }
}
