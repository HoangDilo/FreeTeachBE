package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

public class StudentData {
    private Long id;
    private int grade;
    private String name;
    private String email;
    private String avatarURL;
    private String username;
    private int money;

    public StudentData(Long id, int grade, String name, String email, String avatarURL, String username, int money) {
        this.id = id;
        this.grade = grade;
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
