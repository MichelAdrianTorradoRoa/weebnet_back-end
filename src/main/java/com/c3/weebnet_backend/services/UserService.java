package com.c3.weebnet_backend.services;

import com.c3.weebnet_backend.config.JwtUtil;
import com.c3.weebnet_backend.dto.UserLoginDTO;
import com.c3.weebnet_backend.dto.UserRegisterDTO;
import com.c3.weebnet_backend.entities.User;
import com.c3.weebnet_backend.exceptions.AgeRestrictionException;
import com.c3.weebnet_backend.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    
    public String loginUser(UserLoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getUsernameOrEmail());
        
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(loginDTO.getUsernameOrEmail());
        }
    
        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    
        return jwtUtil.generateToken(user.getUsername());
    }

    
    public List<User> getAllUsers() {
        return userRepository.findAll(); 
    }

    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username); 
    }

    
    public User registerUser(UserRegisterDTO userDTO) {
        validateAge(userDTO.getBirthDate());
        validateEmail(userDTO.getEmail());
        validateUsername(userDTO.getUsername());
    
        
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
    
        
        User newUser = new User(
            userDTO.getUsername(),
            userDTO.getFullname(),
            userDTO.getCellphone(),
            userDTO.getEmail(),
            userDTO.getBirthDate(),
            encryptedPassword
        );
    
        return userRepository.save(newUser);
    }
    
    
    private void validateAge(LocalDate birthDate) {
        if (Period.between(birthDate, LocalDate.now()).getYears() < 14) {
            throw new AgeRestrictionException("You must be at least 14 years old to register.");
        }
    }

    
    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already registered.");
        }
    }

    
    private void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username is already taken.");
        }
    }

    
    public User updateUserProfile(int userId, String bio, String photo, String cellphone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        if (bio != null && !bio.trim().isEmpty()) {
            user.setBio(bio);
        }
        if (photo != null && !photo.trim().isEmpty()) {
            user.setPhoto(photo);
        }
        if (cellphone != null && !cellphone.trim().isEmpty()) {
            user.setCellphone(cellphone);
        }
    
        return userRepository.save(user);
    }
}
