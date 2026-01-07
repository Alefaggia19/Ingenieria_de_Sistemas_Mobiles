package com.checkit.checkit_backend.dto;

public class TaskDto {
    private Long id;
    private String name;
    private int taskOrder;
    private String type;

    // Solution fields needed for creation
    private String qrAnswer;
    private String nfcAnswer;
    private String textAnswer;

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

    // Getters and Setters (Add for the new fields)
    public String getQrAnswer() { return qrAnswer; }
    public void setQrAnswer(String qrAnswer) { this.qrAnswer = qrAnswer; }

    public String getNfcAnswer() { return nfcAnswer; }
    public void setNfcAnswer(String nfcAnswer) { this.nfcAnswer = nfcAnswer; }

    public String getTextAnswer() { return textAnswer; }
    public void setTextAnswer(String textAnswer) { this.textAnswer = textAnswer; }
}