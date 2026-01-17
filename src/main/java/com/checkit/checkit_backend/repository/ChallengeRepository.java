package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // Metodo necessario per "listar challenges propios"
    List<Challenge> findByUserId(Long userId);

    // Resolver KPI: "Número de desafíos creados por semana"
    long countByCreationDateAfter(LocalDateTime date);
}