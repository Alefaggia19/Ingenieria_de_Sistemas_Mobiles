package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring capirà automaticamente: "trova un utente tramite il suo username"
    User findByUsername(String username);
}