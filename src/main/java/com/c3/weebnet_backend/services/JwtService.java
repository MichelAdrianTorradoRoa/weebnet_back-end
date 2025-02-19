package com.c3.weebnet_backend.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import javax.crypto.SecretKey;

@Service
public class JwtService {
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("YBRt6IEvIMhGHIyuWWRfHZ6ib9kLLHNbNWJEFXjHJas=".getBytes());  

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
