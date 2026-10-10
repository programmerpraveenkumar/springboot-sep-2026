package com.app.ms1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "USER-SERVICE",
    fallback = UserClientFallback.class
)
public interface UserClient {

    //api urls added
    @GetMapping("/users/user/list")
    List<String> getUserList();
}