package com.checkit.checkit_backend.repository;

import com.checkit.checkit_backend.model.NpsRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface NpsRepository extends JpaRepository<NpsRating, Long> {
    @Query("SELECT AVG(n.score) FROM NpsRating n")
    Optional<Double> findAverageScore(); // Calcola la media NPS 
}