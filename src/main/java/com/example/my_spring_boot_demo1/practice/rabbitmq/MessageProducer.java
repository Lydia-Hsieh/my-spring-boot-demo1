package com.example.my_spring_boot_demo1.practice.rabbitmq;

import com.example.my_spring_boot_demo1.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
        );
        System.out.println("[x] Sent '" + message + "'");
    }
}
