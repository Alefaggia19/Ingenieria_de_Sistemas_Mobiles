package com.checkit.checkit_backend.dto;

import java.util.List;

public class TaskDetailDTO {

    private Long challengeID;

    private Long id;
    private String name;
    private int taskOrder;

    private List<String> textClue;

    private Long nCompletions;
    private boolean completed;

    private String type;

    public Long getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(Long challengeID) {
        this.challengeID = challengeID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }


    public List<String> getTextClue() {
        return textClue;
    }

    public void setTextClue(List<String> textClue) {
        this.textClue = textClue;
    }

    public Long getnCompletions() {
        return nCompletions;
    }

    public void setnCompletions(Long nCompletions) {
        this.nCompletions = nCompletions;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
