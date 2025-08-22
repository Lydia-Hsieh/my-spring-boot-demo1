package com.example.my_spring_boot_demo1.practice.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

    @Autowired
    private MessageProducer producer;

    @PostMapping("/send")
    public ResponseEntity sendMessageByRabbitMq(@RequestBody String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
