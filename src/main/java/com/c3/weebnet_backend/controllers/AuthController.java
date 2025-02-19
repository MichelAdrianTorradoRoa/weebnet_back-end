package com.c3.weebnet_backend.controllers;

import com.c3.weebnet_backend.dto.UserLoginDTO;
import com.c3.weebnet_backend.dto.UserRegisterDTO;
import com.c3.weebnet_backend.services.AuthService;
import com.c3.weebnet_backend.services.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for handling authentication-related requests.
 * This class provides endpoints for user registration and login, and returns JWT tokens upon successful authentication.
 */
@RestController // Marks this class as a REST controller, capable of handling HTTP requests.
@RequestMapping("/api/auth") // Maps all endpoints in this class to the base path "/api/auth".
@Tag(name = "Authentication", description = "Endpoints for user authentication") // Swagger tag for API documentation.
public class AuthController {

    // Dependencies injected via constructor
    private final AuthService authService; // Service for handling authentication logic.
    private final JwtService jwtService; // Service for generating and managing JWT tokens.

    /**
     * Constructor for dependency injection.
     * @param authService The authentication service.
     * @param jwtService The JWT service.
     */
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user and returns a JWT token.
     * This endpoint accepts user registration data, creates a new user account, and generates a JWT token.
     *
     * @param userRegisterDTO The user registration data.
     * @return A ResponseEntity containing the JWT token if registration is successful, or an error message if it fails.
     */
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns a JWT token.",
        responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration data", content = @Content)
        })
    @PostMapping("/register") // Maps this method to handle POST requests to "/api/auth/register".
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            // Register the user using the AuthService.
            var registeredUser = authService.register(userRegisterDTO);

            // Create a UserDetails object for the registered user.
            UserDetails userDetails = new User(registeredUser.getEmail(), registeredUser.getPassword(), new ArrayList<>());

            // Generate a JWT token for the user.
            String token = jwtService.generateToken(userDetails);

            // Return the token in the response.
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            // Handle errors and return a 400 status with an error message.
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user and returns a JWT token.
     * This endpoint accepts user login credentials, authenticates the user, and generates a JWT token.
     *
     * @param userLoginDTO The user login credentials.
     * @return A ResponseEntity containing the JWT token if authentication is successful, or an error message if it fails.
     */
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
        })
    @PostMapping("/login") // Maps this method to handle POST requests to "/api/auth/login".
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            // Authenticate the user using the AuthService.
            UserDetails userDetails = authService.authenticate(userLoginDTO.getUsernameOrEmail(), userLoginDTO.getPassword());

            // Generate a JWT token for the authenticated user.
            String token = jwtService.generateToken(userDetails);

            // Return the token in the response.
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            // Handle errors and return a 401 status with an error message.
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    /**
     * Inner class representing the token response.
     * This class is used to structure the JSON response containing the JWT token.
     */
    public static class TokenResponse {
        private String token; // The JWT token.

        /**
         * Constructor for creating a TokenResponse object.
         * @param token The JWT token.
         */
        public TokenResponse(String token) {
            this.token = token;
        }

        /**
         * Gets the JWT token.
         * @return The JWT token.
         */
        public String getToken() {
            return token;
        }

        /**
         * Sets the JWT token.
         * @param token The JWT token to set.
         */
        public void setToken(String token) {
            this.token = token;
        }
    }
}