package com.example.my_spring_boot_demo1.practice.rabbitmq;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

    @Autowired
    private MessageProducer producer;

//    @PostMapping("/send")
//    public ResponseEntity sendMessageByRabbitMq(@RequestBody String message) {
//        producer.sendMessage(message);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/send/{key}/{message}")
    public ResponseEntity sendMessageWithRoutingKey(@PathVariable String message, @PathVariable String key) {
        producer.sendMessage(message, key);
        return ResponseEntity.ok().build();
        /**
         * # Direct exchange
         * [x] Sent 'helloA' with key 'route.A'
         * [x] Queue A Received 'helloA'
         * ------------------------------------
         * [x] Sent 'helloB' with key 'route.B'
         * [x] Queue B Received 'helloB'
         *
         * # Fanout exchange
         * [x] Sent 'hello-everyone' with key 'route.A'
         * [x] Queue A Received 'hello-everyone'
         * [x] Queue B Received 'hello-everyone'
         *
         *　# Topic exchange
         * [x] Sent 'hello' with key 'route.one'
         * [x] Queue A Received 'hello'
         * [x] Queue B Received 'hello'
         * ------------------------------------
         * [x] Sent 'hello' with key 'route.one.two'
         * [x] Queue B Received 'hello'
         *
         * # Headers exchange
         * [x] Sent 'hello' with key 'A'
         * [x] Queue A Received 'hello'
         * ------------------------------------
         * [x] Sent 'hello' with key 'B'
         * [x] Queue B Received 'hello'
         */
    }
}
