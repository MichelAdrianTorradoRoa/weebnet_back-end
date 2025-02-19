package com.c3.weebnet_backend.services;

import com.c3.weebnet_backend.config.JwtUtil;
import com.c3.weebnet_backend.dto.UserLoginDTO;
import com.c3.weebnet_backend.dto.UserRegisterDTO;
import com.c3.weebnet_backend.entities.User;
import com.c3.weebnet_backend.repositories.UserRepository;

import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, 
                   JwtUtil jwtUtil, 
                   UserRepository userRepository,
                   PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(UserLoginDTO userLoginDTO) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsernameOrEmail(), userLoginDTO.getPassword())
            );

            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return jwtUtil.generateToken(userDetails.getUsername());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e); 
        }
    }

    public User register(UserRegisterDTO userRegisterDTO) {
        User newUser = new User(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getFullname(),
                userRegisterDTO.getCellphone(),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getBirthDate(),
                passwordEncoder.encode(userRegisterDTO.getPassword())
        );

        return userRepository.save(newUser); 
    }

    public UserDetails authenticate(String usernameOrEmail, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
       
        if (!optionalUser.isPresent() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new Exception("Invalid credentials");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
