package com.checkit.checkit_backend.dto;

import java.time.LocalDateTime;
import java.util.List;





public class ChallengeDto {
    private Long id;
    private String name;
    private String description;
    private String authorName; 
    private LocalDateTime creationDate;
    private List<TaskDto> tasks; 
    private String imageBase64;
    private boolean isAuthor;

    private int completedByCount; //For the description of Desafios
    private boolean saved;

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
    public int getCompletedByCount() { return completedByCount; }
    public void setCompletedByCount(int completedByCount) { this.completedByCount = completedByCount; }
    public boolean isSaved() { return saved; }
    public void setSaved(boolean saved) { this.saved = saved; }
    public boolean isAuthor() { return isAuthor; }
    public void setAuthor(boolean author) { this.isAuthor = author; }

}