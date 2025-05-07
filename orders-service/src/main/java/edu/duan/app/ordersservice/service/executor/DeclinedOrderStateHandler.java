package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import edu.duan.app.ordersservice.service.OrderStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeclinedOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.DECLINED;
    }

    @Override
    public OrderStateEntity handle(OrderEntity orderEntity) {
        return OrderStateEntity.DECLINED;
    }
}
