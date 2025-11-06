package com.checkit.checkit_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp; // Import per la data
import java.time.LocalDateTime; // Import per la data
import java.util.List;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column // Può essere nullo
    private String image; // URL dell'immagine [cite: 98]

    @Column(length = 1000) // Diamo più spazio alla descrizione
    private String description; // [cite: 99]

    @Column(name = "is_ordered")
    private boolean isOrdered; // [cite: 100]

    @CreationTimestamp // Imposta automaticamente la data di creazione
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate; // [cite: 102]

    // Relazione: Molte sfide appartengono a Un Utente (l'autore)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Questa è la Foreign Key (FK) [cite: 101]
    private User user;

    // Relazione: Una sfida ha Molte attività (Task)
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    // Costruttori, Getters e Setters
    public Challenge() {}

    // Getters e Setters (aggiornati)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isOrdered() { return isOrdered; }
    public void setOrdered(boolean ordered) { isOrdered = ordered; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}