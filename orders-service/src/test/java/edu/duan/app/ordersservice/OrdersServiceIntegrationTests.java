package edu.duan.app.ordersservice;

import edu.duan.app.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RabbitListenerTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdersServiceIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String baseUrl;
    private Order createdOrder;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/orders";
        // Створення замовлення перед кожним тестом
        OrderRequest orderRequest = new OrderRequest(1L, 1L, 10, 10.0, "notes", "fulfillmentNotes");
        ResponseEntity<Order> response = restTemplate.postForEntity(baseUrl, orderRequest, Order.class);
        createdOrder = response.getBody();
    }

    @Test
    void testPlaceOrder() {
        OrderRequest orderRequest = new OrderRequest(1L, 1L, 10, 10.0, "notes", "fulfillmentNotes");
        ResponseEntity<Order> response = restTemplate.postForEntity(baseUrl, orderRequest, Order.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderState.NEW, response.getBody().getOrderState());
    }

    @Test
    void testGetOrderById() {
        ResponseEntity<Order> response = restTemplate.getForEntity(baseUrl + "/" + createdOrder.getId(), Order.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdOrder.getId(), response.getBody().getId());
    }

    @Test
    void testGetAllForUser() {
        ResponseEntity<List<Order>> response = restTemplate.exchange(
                baseUrl + "/for/user/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAllForUserByState() {
        ResponseEntity<List<Order>> response = restTemplate.exchange(baseUrl + "/for/user/1/by/state/NEW",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetByCreatedDate() {
        long from = System.currentTimeMillis() - 100000;
        long to = System.currentTimeMillis() + 100000;

        ResponseEntity<List<Order>> response = restTemplate.exchange(baseUrl + "/by/date?from=" + from + "&to=" + to,HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testProcessOrder() {
        ProcessingOrder processingOrder = new ProcessingOrder(
                createdOrder.getId(),
                OrderState.PROCESSING,
                "Опрацьовано успішно",
                "Відвантажено"
        );

        HttpEntity<ProcessingOrder> request = new HttpEntity<>(processingOrder);
        ResponseEntity<OrderState> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, OrderState.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderState.PROCESSING, response.getBody());
    }


    @Test
    void testHandleUserServiceResponse() throws InterruptedException {
        UserEvent message = new UserEvent(
                createdOrder.getUserId(),
                createdOrder.getId(),
                true
        );

        rabbitTemplate.convertAndSend("lab2.topic", "user.events", message);

        Thread.sleep(1000);

        ResponseEntity<Order> response = restTemplate.getForEntity(baseUrl + "/" + createdOrder.getId(), Order.class);
        assertEquals(OrderState.PROCESSING, response.getBody().getOrderState());
    }
}
