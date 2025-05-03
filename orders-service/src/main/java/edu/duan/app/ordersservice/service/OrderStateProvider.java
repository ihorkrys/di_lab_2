package edu.duan.app.ordersservice.service;

import edu.duan.app.api.OrderState;
import edu.duan.app.ordersservice.service.executor.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderStateProvider {
    private final NewOrderStateHandler newOrderStateHandler;
    private final ProcessingOrderStateHandler processingOrderStateHandler;
    private final CompletedOrderStateHandler completedOrderStateHandler;
    private final CanceledOrderStateHandler canceledOrderStateHandler;
    private final DeclinedOrderStateHandler declinedOrderStateHandler;
    private final FulfilledOrderStateHandler fulfilledOrderStateHandler;
    private final RefundedOrderStateHandler refundedOrderStateHandler;

    public OrderStateHandler getOrderStateHandler(OrderState orderState) {
        return switch (orderState) {
            case NEW -> newOrderStateHandler;
            case PROCESSING -> processingOrderStateHandler;
            case COMPLETED -> completedOrderStateHandler;
            case FULFILLED -> fulfilledOrderStateHandler;
            case REFUNDED -> refundedOrderStateHandler;
            case CANCELLED -> canceledOrderStateHandler;
            case DECLINED -> declinedOrderStateHandler;
        };
    }
}
