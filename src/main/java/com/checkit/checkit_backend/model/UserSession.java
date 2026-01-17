package com.checkit.checkit_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime startTime; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserSession() {}
    public UserSession(User user) { this.user = user; }
}