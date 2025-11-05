package com.checkit.checkit_backend.model;

import java.util.List;

public class Challenge {
    private Long id;
    private String name;
    private String author;
    private String description;
    private List<Task> tasks;

    public Challenge() {}

    public Challenge(Long id, String name, String author, String description, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.tasks = tasks;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
