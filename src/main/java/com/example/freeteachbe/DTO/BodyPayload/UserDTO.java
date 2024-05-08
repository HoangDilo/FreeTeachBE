package com.example.freeteachbe.DTO.BodyPayload;

import com.example.freeteachbe.Entity.Role;

public class UserDTO {
    private String name;
    private String email;
    private String avatarURL;
    private String username;
    private String password;
    private Role role;

    public UserDTO(String name, String email, String avatarURL, String username, String password, Role role) {
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
