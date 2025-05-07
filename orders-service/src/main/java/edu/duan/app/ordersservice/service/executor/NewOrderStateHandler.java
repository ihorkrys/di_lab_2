package edu.duan.app.ordersservice.service.executor;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import edu.duan.app.ordersservice.service.OrderStateHandler;

@Component
@AllArgsConstructor
public class NewOrderStateHandler implements OrderStateHandler {

    @Override
    public OrderState getOrderState() {
        return OrderState.NEW;
    }

    @Override
    @Transactional
    public OrderStateEntity handle(OrderEntity orderEntity) {
        return OrderStateEntity.NEW;
    }
}
