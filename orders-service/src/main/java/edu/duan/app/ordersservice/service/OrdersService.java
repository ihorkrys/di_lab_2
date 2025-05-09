package edu.duan.app.ordersservice.service;

import edu.duan.app.api.Order;
import edu.duan.app.api.OrderEvent;
import edu.duan.app.api.OrderState;
import edu.duan.app.api.OrderRequest;
import edu.duan.app.ordersservice.data.*;
import edu.duan.app.ordersservice.exception.ItemNotFoundException;
import edu.duan.app.ordersservice.exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class OrdersService {
    private OrdersRepository ordersRepository;
    private OrderStateProvider orderStateProvider;
    private RabbitPublisher rabbitPublisher;

    public Order get(long id) {
        return ordersRepository.findById(id).map(this::convertToApi).orElseThrow(orderNotFoundException(id));
    }

    public List<Order> getAllForUser(Long userId) {
        return ordersRepository.findAllByUserId(userId).stream().map(this::convertToApi).toList();
    }

    public List<Order> getByCreatedDate(long from, long to) {
        return ordersRepository.findAllByCreatedDateBetween(new java.sql.Date(from), new java.sql.Date(to))
                .stream().map(this::convertToApi).toList();
    }

    public List<Order> getAllForUserByState(Long userId, OrderState orderState) {
        return ordersRepository.findAllByUserIdAndState(userId, OrderStateEntity.valueOf(orderState.name()))
                .stream().map(this::convertToApi).toList();
    }

    public List<Order> getAllByState(OrderState orderState) {
        return ordersRepository.findAllByState(OrderStateEntity.valueOf(orderState.name()))
                .stream().map(this::convertToApi).toList();
    }


    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setItemId(orderRequest.getItemId());
        order.setOrderState(OrderState.NEW);
        order.setCount(orderRequest.getCount());
        order.setItemPrice(orderRequest.getPrice());
        order.setNotes(orderRequest.getNotes());
        order.setFulfillmentNotes(orderRequest.getFulfillmentNotes());
        OrderEntity orderEntity = convertToDomain(order);
        OrderStateEntity orderStateEntity = orderStateProvider.getOrderStateHandler(order.getOrderState()).handle(orderEntity);
        orderEntity.setState(orderStateEntity);
        OrderEntity newOrder = ordersRepository.save(orderEntity);
        rabbitPublisher.publishOrderEvent(buildOrderEvent(newOrder));
        return convertToApi(newOrder);
    }

    @Transactional
    public OrderState processOrder(long orderId, OrderState newOrderState, String notes, String fulfillmentNotes, String reason) {
        System.out.println("Start processing order " + orderId);
        System.out.println("State " + newOrderState);
        OrderEntity orderEntity = ordersRepository.findById(orderId).orElseThrow(orderNotFoundException(orderId));

        if (notes != null && !notes.isBlank()) {
            orderEntity.setNotes(notes);
        }
        if (reason != null && !reason.isBlank()) {
            orderEntity.setStateReasonCode(reason);
        }
        if (newOrderState == OrderState.FULFILLED && fulfillmentNotes != null && !fulfillmentNotes.isBlank()) {
            orderEntity.setFulfillmentNotes(fulfillmentNotes);
        }
        OrderStateEntity orderStateEntity = orderStateProvider.getOrderStateHandler(newOrderState).handle(orderEntity);
        orderEntity.setState(orderStateEntity);
        OrderEntity newStateOrder = ordersRepository.save(orderEntity);
        System.out.println("Processed order " + orderId);
        System.out.println("State " + newStateOrder.getState());
        rabbitPublisher.publishOrderEvent(buildOrderEvent(newStateOrder));
        return OrderState.valueOf(orderStateEntity.name());
    }

    private Order convertToApi(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setCount(orderEntity.getCount());
        order.setTotal(orderEntity.getTotal());
        order.setNotes(orderEntity.getNotes());
        order.setFulfillmentNotes(orderEntity.getFulfillmentNotes());
        order.setItemId(orderEntity.getItemId());
        order.setUserId(orderEntity.getUserId());
        order.setItemPrice(orderEntity.getItemPrice());


        if (orderEntity.getCreatedDate() != null) {
            order.setCreatedDate(new Date(orderEntity.getCreatedDate().getTime()));
        }
        if (orderEntity.getUpdatedDate() != null) {
            order.setUpdatedDate(new Date(orderEntity.getUpdatedDate().getTime()));
        }
        if (orderEntity.getState() != null) {
            order.setOrderState(OrderState.valueOf(orderEntity.getState().name()));
        }
        return order;
    }

    private OrderEntity convertToDomain(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setItemId(order.getItemId());
        orderEntity.setUserId(order.getUserId());
        orderEntity.setCount(order.getCount());
        orderEntity.setItemPrice(order.getItemPrice());
        orderEntity.setTotal(order.getItemPrice() * order.getCount());
        orderEntity.setNotes(order.getNotes());
        orderEntity.setFulfillmentNotes(order.getFulfillmentNotes());
        orderEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setState(OrderStateEntity.valueOf(order.getOrderState().name()));
        return orderEntity;
    }


    private OrderEvent buildOrderEvent(OrderEntity orderEntity) {
        return OrderEvent.builder()
                .withId(orderEntity.getId())
                .withItemId(orderEntity.getItemId())
                .withUserId(orderEntity.getUserId())
                .withCount(orderEntity.getCount())
                .withOrderState(OrderState.valueOf(orderEntity.getState().name()))
                .build();
    }

    private static Supplier<OrderNotFoundException> orderNotFoundException(long id) {
        return () -> new OrderNotFoundException("Order with `" + id + "` not found");
    }

    private static Supplier<ItemNotFoundException> itemNotFoundException(long id) {
        return () -> new ItemNotFoundException("Order with `" + id + "` not found");
    }
}
