package com.app1.user_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/user/list")
    public ResponseEntity<?> getUserList(){
        return ResponseEntity.ok(List.of("user1","user2","user3"));
    }
}
