package com.example.freeteachbe.DTO.BodyPayload;

import java.util.Set;

public class StudentDTO {
    private int grade;
    private Set<Long> subject_ids;

    public StudentDTO(int grade, Long user_id, Set<Long> subject_ids) {
        this.grade = grade;
        this.subject_ids = subject_ids;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    public Set<Long> getSubject_ids() {
        return subject_ids;
    }

    public void setSubject_ids(Set<Long> subject_ids) {
        this.subject_ids = subject_ids;
    }
}
