package com.checkit.checkit_backend.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name = "users") // Specifica il nome della tabella nel DB
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrementante
    private Long id;

    @Column(nullable = false, unique = true) // Non può essere nullo ed è unico
    private String username;

    @Column(nullable = false)
    private String password; // it stores the password hash

    // Relazione: Un utente può creare Molte sfide
    @OneToMany(mappedBy = "user") // "mappedBy" punta al campo 'user' in Challenge.java
    private List<Challenge> createdChallenges;

    // TODO: Aggiungere le altre relazioni (savedChallenges, completedTasks)


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return the user's roles here (e.g., ROLE_USER, ROLE_ADMIN)
        // For a simple app, return an empty list or a default role
        return Collections.emptyList();
    }





    // Costruttori, Getters e Setters
    public User() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Challenge> getCreatedChallenges() { return createdChallenges; }
    public void setCreatedChallenges(List<Challenge> createdChallenges) { 
        this.createdChallenges = createdChallenges; 
    }
}