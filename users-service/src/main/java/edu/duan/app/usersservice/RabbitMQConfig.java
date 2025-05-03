package edu.duan.app.usersservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue orderQueue() {
        return new Queue("order.queue");
    }

    @Bean
    public Queue orderErrorsQueue() {
        return new Queue("orders.error.queue");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user.queue");
    }


    @Bean
    public TopicExchange labExchange() {
        return new TopicExchange("lab2.topic");
    }

    @Bean
    public Binding orderServiceBinding() {
        return BindingBuilder.bind(orderQueue()).to(labExchange()).with("order.events");
    }

    @Bean
    public Binding warehouseServiceBinding() {
        return BindingBuilder.bind(orderErrorsQueue()).to(labExchange()).with("orders.error.events");
    }

    @Bean
    public Binding userServiceBinding() {
        return BindingBuilder.bind(userQueue()).to(labExchange()).with("user.events");
    }
}