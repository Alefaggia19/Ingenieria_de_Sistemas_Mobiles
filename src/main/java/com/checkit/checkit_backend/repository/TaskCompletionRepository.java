package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.TaskCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Long> {
    // Controlla se l'utente ha già completato questa task
    boolean existsByUserIdAndTaskId(Long userId, Long taskId);
    long countByCompletedAtAfter(LocalDateTime date);
    long countByTaskId(Long taskId);

    void deleteByTaskId(Long taskId);
    //Conta i completamenti per un utente filtrando su una lista di ID task
    long countByUserIdAndTaskIdIn(Long userId, java.util.Collection<Long> taskIds);
}