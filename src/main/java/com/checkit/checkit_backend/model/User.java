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


    @Column(nullable = false) // Non può essere nullo ed è unico
    private String realname;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // it stores the password hash

    //  Un user can create differt challenges
    @OneToMany(mappedBy = "user") 
    private List<Challenge> createdChallenges;

    

    @ManyToMany
    @JoinTable(
        name = "challenge_user_saved",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "challenge_id")
    )
    private List<Challenge> savedChallenges;

    // Getter e Setter per savedChallenges
    public List<Challenge> getSavedChallenges() { return savedChallenges; }
    public void setSavedChallenges(List<Challenge> savedChallenges) { this.savedChallenges = savedChallenges; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return the user's roles here (e.g., ROLE_USER, ROLE_ADMIN)
        // For a simple app, return an empty list or a default role
        return Collections.emptyList();
    }

    @ManyToMany
    @JoinTable(
        name = "task_completion",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> completedTasks;

    // Getter e Setter per completedTasks
    public List<Task> getCompletedTasks() { return completedTasks; }
    public void setCompletedTasks(List<Task> completedTasks) { this.completedTasks = completedTasks; }


    // Getters e Setters
    public User() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @Override
    public String getUsername() { return email; }
    public void setUsername(String username) { this.email = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Challenge> getCreatedChallenges() { return createdChallenges; }
    public void setCreatedChallenges(List<Challenge> createdChallenges) { 
        this.createdChallenges = createdChallenges; 
    }
}