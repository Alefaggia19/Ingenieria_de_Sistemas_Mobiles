package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // Potremmo aggiungere qui i metodi
} 