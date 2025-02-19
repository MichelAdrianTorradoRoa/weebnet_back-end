package com.c3.weebnet_backend.config;

import com.c3.weebnet_backend.security.JwtAuthenticationFilter;
import com.c3.weebnet_backend.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration class for the application.
 * This class configures Spring Security settings, including authentication, authorization,
 * CORS, and JWT-based security.
 */
@Configuration // Marks this class as a configuration class for Spring.
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC integration.
public class SecurityConfig implements WebMvcConfigurer {

    // Dependencies injected via constructor
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filter for JWT-based authentication.
    private final CustomUserDetailsService userDetailsService; // Custom user details service for authentication.
    private final PasswordEncoder passwordEncoder; // Password encoder for securing passwords.

    /**
     * Constructor for dependency injection.
     * @param jwtAuthenticationFilter The JWT authentication filter.
     * @param userDetailsService The custom user details service.
     * @param passwordEncoder The password encoder.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Defines the AuthenticationManager bean.
     * @param authenticationConfiguration The authentication configuration.
     * @return An instance of AuthenticationManager.
     * @throws Exception If an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defines the UserDetailsService bean.
     * @return The custom user details service.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * Defines the DaoAuthenticationProvider bean.
     * This provider uses the custom UserDetailsService and PasswordEncoder for authentication.
     * @return An instance of DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Set the custom user details service.
        authProvider.setPasswordEncoder(passwordEncoder); // Set the password encoder.
        return authProvider;
    }

    /**
     * Configures the security filter chain.
     * This method defines the security rules for the application, including CORS, CSRF, and JWT-based authentication.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS configuration.
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection (not needed for stateless APIs).
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/auth/**", "/api/users/register", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Allow public access to these endpoints.
                        .anyRequest().authenticated() // Require authentication for all other endpoints.
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions (no session cookies).
                .authenticationProvider(authenticationProvider()) // Set the authentication provider.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add the JWT filter before the default username/password filter.
                .build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings.
     * This method allows requests from specific origins and with specific methods and headers.
     * @return A CorsConfigurationSource instance.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Allow requests from this origin.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Allow these HTTP methods.
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers.
        configuration.setAllowCredentials(true); // Allow credentials (e.g., cookies).

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this configuration to all paths.
        return source;
    }
}