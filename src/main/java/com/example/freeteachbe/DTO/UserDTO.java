package com.example.freeteachbe.DTO;

public class UserDTO {
    private String name;
    private String email;
    private String avatarURL;
    private String username;
    private String password;

    public UserDTO(String name, String email, String avatarURL, String username, String password, int money) {
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.password = password;
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
    
}
