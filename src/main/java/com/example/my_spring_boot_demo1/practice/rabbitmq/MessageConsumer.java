package com.example.my_spring_boot_demo1.practice.rabbitmq;

import com.example.my_spring_boot_demo1.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_A)
    public void receiveMessageFromQueueA(String message) {
        System.out.println("[x] Queue A Received '" + message + "'");
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_B)
    public void  receiveMessageFromQueueB(String message) {
        System.out.println("[x] Queue B Received '" + message + "'");
    }
}
