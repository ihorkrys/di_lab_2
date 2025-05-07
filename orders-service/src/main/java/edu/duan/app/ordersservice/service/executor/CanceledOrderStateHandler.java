package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
@RequiredArgsConstructor
public class CanceledOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.CANCELLED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        return OrderStateEntity.CANCELLED;
    }
}
