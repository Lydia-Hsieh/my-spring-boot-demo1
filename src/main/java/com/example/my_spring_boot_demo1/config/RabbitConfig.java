package com.example.my_spring_boot_demo1.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME_A = "demo.queue.A";
    public static final String QUEUE_NAME_B = "demo.queue.B";
    public static final String EXCHANGE_NAME = "demo.exchange";
//    public static final String ROUTING_KEY = "demo.key";

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_NAME_A, false);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_NAME_B, false);
    }

    /**
     * direct exchange
     */
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding bindingA(Queue queueA, DirectExchange exchange) {
//        return BindingBuilder.bind(queueA).to(exchange).with("route.A");
//    }
//
//    @Bean
//    public Binding bindingB(Queue queueB, DirectExchange exchange) {
//        return BindingBuilder.bind(queueB).to(exchange).with("route.B");
//    }

    /**
     * fanout exchange
     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding bindingFanoutA(Queue queueA, FanoutExchange exchange) {
//        return BindingBuilder.bind(queueA).to(exchange);
//    }
//
//    @Bean
//    public Binding bindingFanoutB(Queue queueB, FanoutExchange exchange) {
//        return BindingBuilder.bind(queueB).to(exchange);
//    }

    /**
     * topic exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingTopicA(Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with("route.*");
    }

    @Bean
    public Binding bindingTopicB(Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with("route.#");
    }
}
