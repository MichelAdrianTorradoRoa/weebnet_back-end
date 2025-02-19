package com.c3.weebnet_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for defining beans and application-wide settings.
 * This class is responsible for setting up the password encoder used in the application.
 */
@Configuration // Indicates that this class contains Spring configuration definitions.
public class AppConfig {

    /**
     * Defines a bean for the password encoder.
     * The BCryptPasswordEncoder is a widely used implementation for securely hashing passwords.
     * 
     * @return A BCryptPasswordEncoder instance to be used for password encoding.
     */
    @Primary // Marks this bean as the primary choice when multiple candidates are available.
    @Bean // Indicates that this method returns a bean to be managed by the Spring container.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Returns a new instance of BCryptPasswordEncoder.
    }
}