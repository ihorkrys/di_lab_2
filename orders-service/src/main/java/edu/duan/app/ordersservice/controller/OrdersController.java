package edu.duan.app.ordersservice.controller;

import edu.duan.app.api.Order;
import edu.duan.app.api.OrderRequest;
import edu.duan.app.api.OrderState;
import edu.duan.app.api.ProcessingOrder;
import edu.duan.app.ordersservice.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping(path = "/{id}")
    public Order get(@PathVariable int id) {
        return ordersService.get(id);
    }

    @GetMapping("/for/user")
    public List<Order> getAllForUser(@RequestParam int userId) {
        return ordersService.getAllForUser(userId);
    }

    @GetMapping(path = "/for/user/by/state")
    public List<Order> getAllForUserByState(@RequestParam int userId, @RequestParam OrderState state) {
        return ordersService.getAllForUserByState(userId, state);
    }

    @GetMapping(path = "/by/date")
    public List<Order> getByCreatedDate(@RequestParam long from, @RequestParam long to) {
        return ordersService.getByCreatedDate(from, to);
    }

    @PostMapping()
    public @ResponseBody OrderState placeOrder(@RequestBody OrderRequest order) {
        return ordersService.placeOrder(order);
    }

    @PutMapping()
    public @ResponseBody OrderState processOrder(@RequestBody ProcessingOrder processingOrder) {
        return ordersService.processOrder(processingOrder.getId(), processingOrder.getOrderState(), processingOrder.getNotes(), processingOrder.getFulfillmentNotes(), null);
    }
}
