package com.checkit.checkit_backend.dto;

public class TaskDto {
    private Long id;
    private String name;
    private int taskOrder;
    private String type;

    // Costruttori, Getters, Setters
    public TaskDto() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getTaskOrder() { return taskOrder; }
    public void setTaskOrder(int taskOrder) { this.taskOrder = taskOrder; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}