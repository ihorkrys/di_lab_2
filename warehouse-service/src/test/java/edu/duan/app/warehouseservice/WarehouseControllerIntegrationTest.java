package edu.duan.app.warehouseservice;

import edu.duan.app.api.WarehouseItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WarehouseControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private WarehouseItem createdWarehouseItem;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/warehouse";
        WarehouseItem wiRequest = new WarehouseItem();
        wiRequest.setName("Test Product");
        wiRequest.setDescription("Test Description");
        wiRequest.setInStockCount(10);
        wiRequest.setPrice(100.0);
        ResponseEntity<WarehouseItem> response = restTemplate.postForEntity(baseUrl, wiRequest, WarehouseItem.class);
        createdWarehouseItem = response.getBody();
    }

    @Test
    void testGetById() {
        WarehouseItem wiRequest = new WarehouseItem();
        wiRequest.setName("Product");
        wiRequest.setDescription("Description");
        wiRequest.setInStockCount(1);
        wiRequest.setPrice(10.0);

        ResponseEntity<WarehouseItem> response = restTemplate.getForEntity(baseUrl + "/" + createdWarehouseItem.getId(), WarehouseItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdWarehouseItem.getName(), response.getBody().getName());
        assertEquals(createdWarehouseItem.getDescription(), response.getBody().getDescription());
        assertEquals(createdWarehouseItem.getInStockCount(), response.getBody().getInStockCount());
        assertEquals(createdWarehouseItem.getPrice(), response.getBody().getPrice());
    }

    @Test
    void testAddItem() {
        WarehouseItem wiRequest = new WarehouseItem();
        wiRequest.setName("Product");
        wiRequest.setDescription("Description");
        wiRequest.setInStockCount(1);
        wiRequest.setPrice(10.0);

        ResponseEntity<WarehouseItem> response = restTemplate.postForEntity(baseUrl, wiRequest, WarehouseItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product", response.getBody().getName());
        assertEquals("Description", response.getBody().getDescription());
        assertEquals(1, response.getBody().getInStockCount());
        assertEquals(10.0, response.getBody().getPrice());
    }

    @Test
    void testGetAllItems() {
        ResponseEntity<List<WarehouseItem>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WarehouseItem>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testUpdateQuantity() {
        ResponseEntity<WarehouseItem> response = restTemplate.exchange(
                baseUrl + "/" + createdWarehouseItem.getId() + "/3",
                HttpMethod.PUT,
                null,
                WarehouseItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdWarehouseItem.getId(), response.getBody().getId());
        assertEquals(3, response.getBody().getInStockCount());
    }
}