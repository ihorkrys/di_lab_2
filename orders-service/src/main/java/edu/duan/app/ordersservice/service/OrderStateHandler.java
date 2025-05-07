package edu.duan.app.ordersservice.service;


import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.data.OrderEntity;
import edu.duan.app.ordersservice.data.OrderStateEntity;

public interface OrderStateHandler {
    OrderState getOrderState();
    OrderStateEntity handle(OrderEntity orderEntity);
}
