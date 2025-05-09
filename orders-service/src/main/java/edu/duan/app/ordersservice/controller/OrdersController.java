package edu.duan.app.ordersservice.controller;

import edu.duan.app.api.Order;
import edu.duan.app.api.OrderRequest;
import edu.duan.app.api.OrderState;
import edu.duan.app.api.ProcessingOrder;
import edu.duan.app.ordersservice.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Замовлення", description = "API для управління замовленнями")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Operation(summary = "Отримати замовлення за ID", description = "Повертає одне замовлення за його унікальним ідентифікатором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Замовлення знайдено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Замовлення не знайдено")
    })
    @GetMapping(path = "/{id}")
    public @ResponseBody Order get(
            @Parameter(description = "ID замовлення, яке потрібно отримати") @PathVariable("id") long id) {
        return ordersService.get(id);
    }

    @Operation(summary = "Отримати всі замовлення користувача", description = "Повертає всі замовлення, що належать конкретному користувачу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список замовлень",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)))
    })
    @GetMapping("/for/user/{id}")
    public @ResponseBody List<Order> getAllForUser(
            @Parameter(description = "ID користувача, чиї замовлення потрібно отримати") @PathVariable("id") long userId) {
        return ordersService.getAllForUser(userId);
    }

    @Operation(summary = "Отримати всі замовлення користувача за станом", description = "Повертає всі замовлення користувача, відфільтровані за станом замовлення.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список замовлень",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)))
    })
    @GetMapping(path = "/for/user/{id}/by/state/{state}")
    public @ResponseBody List<Order> getAllForUserByState(
            @Parameter(description = "ID користувача, чиї замовлення потрібно отримати") @PathVariable("id") long userId,
            @Parameter(description = "Стан замовлення для фільтрації") @PathVariable("state") OrderState state) {
        return ordersService.getAllForUserByState(userId, state);
    }

    @Operation(summary = "Отримати замовлення за датою створення", description = "Повертає всі замовлення, створені у певному діапазоні дат (timestamps).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список замовлень",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)))
    })
    @GetMapping(path = "/by/date")
    public @ResponseBody List<Order> getByCreatedDate(
            @Parameter(description = "Початок діапазону дат (timestamp)") @RequestParam("from") long from,
            @Parameter(description = "Кінець діапазону дат (timestamp)") @RequestParam("to") long to) {
        return ordersService.getByCreatedDate(from, to);
    }

    @Operation(summary = "Створити нове замовлення", description = "Надсилає нове замовлення для обробки.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Замовлення створено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Невірний запит")
    })
    @PostMapping()
    public @ResponseBody Order placeOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Тіло запиту для створення замовлення",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OrderRequest.class))
            )
            @RequestBody OrderRequest order) {
        return ordersService.placeOrder(order);
    }

    @Operation(summary = "Обробити замовлення", description = "Оновлює статус і додаткову інформацію існуючого замовлення.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Замовлення оброблено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderState.class))),
            @ApiResponse(responseCode = "400", description = "Невірний запит"),
            @ApiResponse(responseCode = "404", description = "Замовлення не знайдено")
    })
    @PutMapping()
    public @ResponseBody OrderState processOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Тіло запиту для обробки замовлення",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProcessingOrder.class))
            )
            @RequestBody ProcessingOrder processingOrder) {
        return ordersService.processOrder(
                processingOrder.getId(),
                processingOrder.getOrderState(),
                processingOrder.getNotes(),
                processingOrder.getFulfillmentNotes(),
                null
        );
    }
}
