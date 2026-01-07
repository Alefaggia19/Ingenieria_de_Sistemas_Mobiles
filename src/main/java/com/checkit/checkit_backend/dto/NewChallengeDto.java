package com.checkit.checkit_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewChallengeDto {

    private String name;
    private String description;
    private boolean isOrdered; 
    private String imageUrl;
    private List<TaskDto> tasks; // List of tasks to be created immediately


    // Costruttori, Getters, Setters
    public NewChallengeDto() {}

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isOrdered() { return isOrdered; }
    public void setOrdered(boolean ordered) { isOrdered = ordered; }

    public List<TaskDto> getTasks() { return tasks; }
    public void setTasks(List<TaskDto> tasks) { this.tasks = tasks; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

}
