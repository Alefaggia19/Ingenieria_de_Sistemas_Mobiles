package com.checkit.checkit_backend.repository;


import com.checkit.checkit_backend.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    // Il count() standard servirà per la Tasa de Conversión 
}
