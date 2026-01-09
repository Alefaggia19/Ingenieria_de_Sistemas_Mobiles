package com.checkit.checkit_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewChallengeDto {

    private String name;
    private String description;
    private boolean isOrdered; 
    private String imageBase64; // Modified from imageUrl to imageBase64
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

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}
