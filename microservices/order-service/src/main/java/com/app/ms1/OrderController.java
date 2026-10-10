package com.app.ms1;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private  final MessageProducer messageProducer;
    private final UserClient userClient;

    public OrderController(UserClient userClient,MessageProducer messageProducer) {
        this.userClient = userClient;
        this.messageProducer = messageProducer;
    }


    @GetMapping("/order/list")
    public ResponseEntity<?> getOrderList(){
        return ResponseEntity.ok(List.of("order1","order2","order3"));
    }
    @GetMapping("/users")
    public List<String> getUsers() {
        return userClient.getUserList();
    }

        @GetMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String message) {
        try{
            messageProducer.sendMessage(message);
            return ResponseEntity.ok("");
        } catch (Exception e) {
                return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
