package com.checkit.checkit_backend.dto;

public class UserDTO {

    Long id;
    String username;

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}
