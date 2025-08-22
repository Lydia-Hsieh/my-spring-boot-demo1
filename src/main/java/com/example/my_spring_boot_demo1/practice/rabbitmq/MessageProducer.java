package com.example.my_spring_boot_demo1.practice.rabbitmq;

import com.example.my_spring_boot_demo1.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                routingKey,
                message
        );
//        MessageProperties props = new MessageProperties();
//        props.setHeader("type", routingKey);
//        Message msg = new Message(message.getBytes(), props);
//        rabbitTemplate.convertAndSend(
//                RabbitConfig.EXCHANGE_NAME,
//                "",
//                msg
//        );
        System.out.println("[x] Sent '" + message + "' with key '" + routingKey + "'");
    }
}
