package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring capirà: "trova tutte le task per un dato challengeId"
    List<Task> findByChallengeId(Long challengeId);
}