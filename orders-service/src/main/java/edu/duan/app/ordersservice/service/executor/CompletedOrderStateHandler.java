package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.exception.UnsupportedStateOfOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
@RequiredArgsConstructor
public class CompletedOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.COMPLETED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        switch(orderEntity.getState()) {
            case PROCESSING, FULFILLED -> {
                return OrderStateEntity.COMPLETED;
            }
            default -> throw new UnsupportedStateOfOrderException("Order state not supported. State: " + orderEntity.getState());
        }
    }
}
