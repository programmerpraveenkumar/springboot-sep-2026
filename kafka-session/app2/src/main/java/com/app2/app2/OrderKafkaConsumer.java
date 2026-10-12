package com.app2.app2;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderKafkaConsumer {

    @KafkaListener(
            topics = "order1"

    )
    public  void orderRecvd(String orderid){
        System.out.println( "recvd");
        System.out.println( orderid);
    }
}
