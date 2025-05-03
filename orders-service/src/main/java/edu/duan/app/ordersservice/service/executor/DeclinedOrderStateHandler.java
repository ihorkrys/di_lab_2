package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.service.OrderStateHandler;
import edu.duan.app.ordersservice.service.RabbitPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeclinedOrderStateHandler implements OrderStateHandler {
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Override
    public OrderState getOrderState() {
        return OrderState.DECLINED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        orderEntity.setState(OrderStateEntity.DECLINED);
        rabbitPublisher.publishOrderEvent(buildOrderEvent(orderEntity));

        return orderEntity.getState();
    }
}
