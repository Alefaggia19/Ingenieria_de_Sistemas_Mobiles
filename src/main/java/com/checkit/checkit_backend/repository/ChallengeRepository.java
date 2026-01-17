package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // Metodo necessario per "listar challenges propios"
    List<Challenge> findByUserId(Long userId);

    // Resolver KPI: "Número de desafíos creados por semana"
    long countByCreationDateAfter(LocalDateTime date);
//    @Query("SELECT DISTINCT c FROM challenges c " +
//            "JOIN tasks t " +
//            "JOIN task_completions tc ON t.ctc.task = t.id")
//    List<Challenge> findAllInProgressByUserId(Long userID);
@Query("SELECT DISTINCT c FROM Challenge c " +
        "JOIN c.tasks t " +
        "JOIN TaskCompletion tc ON tc.task.id = t.id " +
        "WHERE tc.user.id = :userId")
List<Challenge> findAllChallengesWithCompletionsByUser(@Param("userId") Long userId);
}