package com.example.freeteachbe.DTO.BodyPayload;

public class SubjectDTO {
    private String subjectName;

    public SubjectDTO(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectDTO() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
