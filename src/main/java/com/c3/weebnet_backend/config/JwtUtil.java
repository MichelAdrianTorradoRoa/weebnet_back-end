package com.c3.weebnet_backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    // Constructor initializes the secret key and token expiration time
    public JwtUtil(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.expiration = jwtProperties.getExpiration() * 1000L;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    // Generates a JWT token for a given username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extracts the username from the given JWT token
    public String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException("Token is null or empty");
        }
    
        try {
            return getClaims(token).getSubject();
        } catch (JwtException e) {
            throw new JwtException("Error extracting the username from the token: " + e.getMessage());
        }
    }

    // Validates the token by checking its integrity and expiration
    public boolean validateToken(String token, String username) {
        try {
            return username.equals(extractUsername(token)) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false; 
        }
    }

    // Retrieves claims from the JWT token
    private Claims getClaims(String token) throws JwtException {
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException("Token is null or empty");
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Error processing the token: " + e.getMessage());
        }
    }

    // Checks if the token is expired
    private boolean isTokenExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (JwtException e) {
            return true; 
        }
    }
}
