package com.app1.app1;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    final KafkaTemplate<String,String> kafkaTemplate;
    public OrderController(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @GetMapping("/order")
    public ResponseEntity<?> getData(@RequestParam String orderId){
        System.out.println("order id ready to publish "+orderId);
        kafkaTemplate.send("order1",orderId);
        return ResponseEntity.ok("");
    }
}
