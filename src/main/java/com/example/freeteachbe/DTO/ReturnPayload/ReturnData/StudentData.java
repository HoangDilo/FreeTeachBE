package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

public class StudentData extends UserData {
    private int grade;

    public StudentData(Long id, int grade, String name, String email, String avatarURL, String username, int money) {
        super(id, name, email, avatarURL, username, money);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
