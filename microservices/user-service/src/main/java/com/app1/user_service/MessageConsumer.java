package com.app1.user_service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "oct4")
    public void recvMsg(String msg){
        System.out.print(msg);
    }
}
