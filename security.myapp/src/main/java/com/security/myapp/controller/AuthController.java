package com.security.myapp.controller;

import com.security.myapp.dto.LoginRequest;
import com.security.myapp.service.CustomUserDetailsService;
import com.security.myapp.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(AuthenticationManager authenticationManager,JwtService jwtService){
        this.authenticationManager=authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            System.out.println(loginRequest.getUsername());
            Authentication authentication =  authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),loginRequest.getPassword()
                    ));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(Map.of("token",token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }

    }


//    @PostMapping("/auth/register")

}
