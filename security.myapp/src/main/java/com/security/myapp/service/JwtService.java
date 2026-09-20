package com.security.myapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
        private final SecretKey secretKey = Keys.hmacShaKeyFor("my-secretkey-for-my-appmy-secretkey-for-my-app".getBytes());
        private final long expiry = 1000*60*60;//1 hour

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiry))
                .signWith(secretKey)
                .compact();
    }

    //get the subject from the token
    public String getuserName(String token){
            return extractAllClaims(token).getSubject();
    }
//    get expiry value and check with current date and time
    public  boolean isTokenValid(String token,UserDetails userDetails){
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
