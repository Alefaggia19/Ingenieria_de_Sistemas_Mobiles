package com.checkit.checkit_backend.dto;

public class UserDTO {

    String username;
    String email;

    public UserDTO(String username,String email) {
        this.username = username;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


}
