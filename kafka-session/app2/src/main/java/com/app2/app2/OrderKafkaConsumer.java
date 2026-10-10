package com.app2.app2;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderKafkaConsumer {

    @KafkaListener(
            topics = "orders",
            groupId = "payment-service"
    )
    public  void orderRecvd(String orderid){
        System.out.println(orderid);
    }
}
