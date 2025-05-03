package edu.duan.app.ordersservice.service;


import edu.duan.app.api.OrderEvent;
import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;

public interface OrderStateHandler {
    public OrderState getOrderState();
    public OrderStateEntity handle(OrderEntity orderEntity);

    default OrderEvent buildOrderEvent(OrderEntity orderEntity) {
        return OrderEvent.builder()
                .withId(orderEntity.getId())
                .withItemId(orderEntity.getItem().getId())
                .withUserId(orderEntity.getUserId())
                .withCount(orderEntity.getCount())
                .withOrderState(getOrderState())
                .build();
    }
}
