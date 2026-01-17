package com.checkit.checkit_backend.dto;

import java.util.List;

public class KpiResponseDto {
    private long totalUsers; 
    private long tasksCompletedToday;
    private long challengesCreatedThisWeek; 
    private double averageNps;  
    private double conversionRate;  

    // Getters e Setters
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTasksCompletedToday() { return tasksCompletedToday; }
    public void setTasksCompletedToday(long tasksCompletedToday) { this.tasksCompletedToday = tasksCompletedToday; }
    public long getChallengesCreatedThisWeek() { return challengesCreatedThisWeek; }
    public void setChallengesCreatedThisWeek(long challengesCreatedThisWeek) { this.challengesCreatedThisWeek = challengesCreatedThisWeek; }
    public double getAverageNps() { return averageNps; }
    public void setAverageNps(double averageNps) { this.averageNps = averageNps; }
    public double getConversionRate() { return conversionRate; }
    public void setConversionRate(double conversionRate) { this.conversionRate = conversionRate; }
}
