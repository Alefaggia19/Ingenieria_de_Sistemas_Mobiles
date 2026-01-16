package com.checkit.checkit_backend.dto;

public class UserDTO {

    String username;
    String email;
    String password;

    public UserDTO(String username,String password,String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
