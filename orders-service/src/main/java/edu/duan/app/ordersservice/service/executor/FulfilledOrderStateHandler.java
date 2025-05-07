package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.exception.UnsupportedStateOfOrderException;
import org.springframework.stereotype.Component;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
public class FulfilledOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.FULFILLED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        switch(orderEntity.getState()) {
            case PROCESSING -> {
                return OrderStateEntity.FULFILLED;
            }
            default -> throw new UnsupportedStateOfOrderException("Order state not supported. State: " + orderEntity.getState());
        }
    }
}
