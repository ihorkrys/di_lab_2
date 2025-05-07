package edu.duan.app.ordersservice.service;

import edu.duan.app.api.OrderErrorEvent;
import edu.duan.app.api.OrderState;
import edu.duan.app.api.UserEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EventListener {
    @Autowired
    private OrdersService ordersService;

    @RabbitListener(queues = "user.queue")
    @Transactional
    public void handleOrder(UserEvent event) {
        System.out.println("Received user event: " + event);
        if (event.isExist()) {
            ordersService.processOrder(event.getOrderId(), OrderState.PROCESSING, null, null, null);
        } else {
            ordersService.processOrder(event.getOrderId(), OrderState.DECLINED, null, null, "USER_NOT_FOUND");
        }
    }

    @RabbitListener(queues = "orders.error.queue")
    @Transactional
    public void handleOrderError(OrderErrorEvent event) {
        ordersService.processOrder(event.getId(), OrderState.DECLINED, null, null, event.getReason_code());
    }

}
