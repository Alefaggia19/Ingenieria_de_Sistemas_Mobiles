package com.checkit.checkit_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clues")
public class Clue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String textClue; // [cite: 142]

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false) // [cite: 143]
    private Task task;

    // Costruttore, Getters e Setters
    public Clue() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTextClue() { return textClue; }
    public void setTextClue(String textClue) { this.textClue = textClue; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}