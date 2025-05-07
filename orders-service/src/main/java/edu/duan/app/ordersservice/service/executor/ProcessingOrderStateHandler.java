package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.exception.UnsupportedStateOfOrderException;
import edu.duan.app.ordersservice.service.OrderStateHandler;
import edu.duan.app.ordersservice.service.RabbitPublisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ProcessingOrderStateHandler implements OrderStateHandler {
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Override
    public OrderState getOrderState() {
        return OrderState.PROCESSING;
    }

    @Override
    @Transactional
    public OrderStateEntity handle(OrderEntity orderEntity) {
        switch(orderEntity.getState()) {
            case NEW -> {
                return OrderStateEntity.PROCESSING;
            }
            default -> throw new UnsupportedStateOfOrderException("Order state not supported. State: " + orderEntity.getState());
        }
    }
}
