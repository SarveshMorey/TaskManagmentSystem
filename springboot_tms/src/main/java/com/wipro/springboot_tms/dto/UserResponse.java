package com.wipro.springboot_tms.dto;

public class UserResponse {
    private Integer userId;
    private String username;
    private String email;

    public UserResponse(Integer userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public Integer getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
