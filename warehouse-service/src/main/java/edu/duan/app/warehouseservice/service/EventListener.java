package edu.duan.app.warehouseservice.service;

import edu.duan.app.api.OrderErrorEvent;
import edu.duan.app.api.OrderEvent;
import edu.duan.app.api.OrderState;
import edu.duan.app.api.WarehouseItem;
import edu.duan.app.warehouseservice.data.ItemEntity;
import edu.duan.app.warehouseservice.data.WarehouseEntity;
import edu.duan.app.warehouseservice.data.WarehouseRepository;
import edu.duan.app.warehouseservice.exception.ItemStockNotFoundException;
import edu.duan.app.warehouseservice.exception.WarehouseItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class EventListener {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @RabbitListener(queues = "order.queue")
    @Transactional
    public void handleOrder(OrderEvent event) {
        switch (event.getOrderState()) {
            case PROCESSING -> warehouseRepository.findById(event.getItemId()).ifPresentOrElse(
                    warehouseEntity -> {
                        if (warehouseEntity.getInStock() <= 0 || warehouseEntity.getInStock() < event.getCount()) {
                            rabbitPublisher.publishOrderErrorEvent(OrderErrorEvent.builder()
                                    .withId(event.getId())
                                    .withUserId(event.getUserId())
                                    .withReason_code("ITEMS_NOT_ENOUGH")
                                    .build());
                        } else {
                            warehouseEntity.setInStock(warehouseEntity.getInStock() - event.getCount());
                            warehouseRepository.save(warehouseEntity);
                        }
                    },
                    () -> rabbitPublisher.publishOrderErrorEvent(OrderErrorEvent.builder()
                            .withId(event.getId())
                            .withUserId(event.getUserId())
                            .withReason_code("ITEM_NOT_FOUND")
                            .build())
            );
            case CANCELLED -> warehouseRepository.findById(event.getItemId()).ifPresent(warehouseEntity -> {
                warehouseEntity.setInStock(warehouseEntity.getInStock() + event.getCount());
                warehouseRepository.save(warehouseEntity);
            });
            default -> System.out.println("Nothing to process");
        }
    }

}
