package com.c3.weebnet_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for JWT (JSON Web Token) properties.
 * This class maps properties from the application configuration file (e.g., application.yml or application.properties)
 * with the prefix "jwt" to the fields in this class.
 */
@Component // Marks this class as a Spring component, making it a candidate for dependency injection.
@ConfigurationProperties(prefix = "jwt") // Binds properties with the prefix "jwt" to this class.
public class JwtProperties {

    // Fields to store JWT configuration properties
    private String secret; // The secret key used for signing the JWT.
    private long expiration; // The expiration time of the JWT in milliseconds.
    private String tokenPrefix; // The prefix used in the token (e.g., "Bearer").
    private String header; // The HTTP header where the JWT is expected (e.g., "Authorization").

    // Getter and Setter methods for the fields

    /**
     * Gets the secret key used for signing the JWT.
     * @return The secret key.
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret key used for signing the JWT.
     * @param secret The secret key to set.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets the expiration time of the JWT in milliseconds.
     * @return The expiration time.
     */
    public long getExpiration() {
        return expiration;
    }

    /**
     * Sets the expiration time of the JWT in milliseconds.
     * @param expiration The expiration time to set.
     */
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    /**
     * Gets the prefix used in the token (e.g., "Bearer").
     * @return The token prefix.
     */
    public String getTokenPrefix() {
        return tokenPrefix;
    }

    /**
     * Sets the prefix used in the token (e.g., "Bearer").
     * @param tokenPrefix The token prefix to set.
     */
    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    /**
     * Gets the HTTP header where the JWT is expected (e.g., "Authorization").
     * @return The header name.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the HTTP header where the JWT is expected (e.g., "Authorization").
     * @param header The header name to set.
     */
    public void setHeader(String header) {
        this.header = header;
    }
}