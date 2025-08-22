package com.example.my_spring_boot_demo1.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "demo.queue";
    public static final String EXCHANGE_NAME = "demo.exchange";
    public static final String ROUTING_KEY = "demo.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * direct exchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
