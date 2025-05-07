package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.exception.UnsupportedStateOfOrderException;
import org.springframework.stereotype.Component;
import edu.duan.app.ordersservice.service.OrderStateHandler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefundedOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.REFUNDED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        switch (orderEntity.getState()) {
            case COMPLETED -> {
                return OrderStateEntity.REFUNDED;
            }
            default -> throw new UnsupportedStateOfOrderException("Order state not supported. State: " + orderEntity.getState());
        }
    }
}
