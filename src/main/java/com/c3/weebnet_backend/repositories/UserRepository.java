package com.c3.weebnet_backend.repositories;

import com.c3.weebnet_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsernameOrEmail(String username, String email);

   
    Optional<User> findByUsername(String username);
}
