package com.example.freeteachbe.DTO.BodyPayload;

public class StudentDTO {
    private int grade;
    private Long user_id;
    private Long subject_id;

    public StudentDTO(int grade, Long user_id, Long subject_id) {
        this.grade = grade;
        this.user_id = user_id;
        this.subject_id = subject_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
}
