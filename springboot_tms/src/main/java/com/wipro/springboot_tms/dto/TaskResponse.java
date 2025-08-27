package com.wipro.springboot_tms.dto;

import java.time.LocalDate;

public class TaskResponse {
    private Integer taskId;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private Integer userId;

    public TaskResponse(Integer taskId, String title, String description, String status, LocalDate dueDate, Integer userId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public Integer getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public Integer getUserId() { return userId; }
}
