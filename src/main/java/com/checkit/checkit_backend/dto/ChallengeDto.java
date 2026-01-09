package com.checkit.checkit_backend.dto;

import java.time.LocalDateTime;
import java.util.List;





public class ChallengeDto {
    private Long id;
    private String name;
    private String description;
    private String authorName; // Appiattito da User.username
    private LocalDateTime creationDate;
    private List<TaskDto> tasks; // Usiamo il TaskDto!
    private String imageBase64;

    // Costruttori, Getters, Setters
    public ChallengeDto() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public List<TaskDto> getTasks() { return tasks; }
    public void setTasks(List<TaskDto> tasks) { this.tasks = tasks; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}