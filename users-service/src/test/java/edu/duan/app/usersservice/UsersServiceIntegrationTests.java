package edu.duan.app.usersservice;

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
class UsersServiceIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String baseUrl;
    private User createdUser;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/users";
        User userRequest = new User();
        userRequest.setLogin("ihor.krys@example.com");
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, userRequest, User.class);
        createdUser = response.getBody();
    }

    @Test
    void testCreateUser() {
        User userRequest = new User();
        userRequest.setLogin("test@example.com");
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, userRequest, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getLogin());
    }

    @Test
    void testGetAllUsers() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetById() {
        ResponseEntity<User> response = restTemplate.getForEntity(
                baseUrl + "/" + createdUser.getId(),
                User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdUser.getLogin(), response.getBody().getLogin());
    }

}
