package com.checkit.checkit_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewChallengeDto {


    private String name;
    private String description;


    // Costruttori, Getters, Setters
    public NewChallengeDto() {}

    // Getters e Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


}
