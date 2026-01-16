package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // Spring capirà automaticamente: "trova un utente tramite il suo username"
    Optional<User> findByRealname(String username);

    Optional<User> findByEmail(String email);
}