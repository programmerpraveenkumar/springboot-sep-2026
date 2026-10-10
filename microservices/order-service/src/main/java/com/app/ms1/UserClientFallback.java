package com.app.ms1;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientFallback implements UserClient{
    @Override
    public List<String> getUserList() {
//        return dummy response or error message
//        as of now return the error msg
        return List.of("User Service is unavailable");
    }
}
