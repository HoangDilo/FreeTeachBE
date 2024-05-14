package com.example.freeteachbe.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String subjectName;

    @ManyToMany(mappedBy = "subjects")
    private Set<StudentEntity> students;
    @ManyToMany(mappedBy = "subjects")
    private Set<TeacherEntity> teachers;
    @OneToMany(mappedBy = "subject")
    private Set<DocumentPostEntity> documentPosts;

    public SubjectEntity(Long id, String subjectName, Set<StudentEntity> students) {
        this.id = id;
        this.subjectName = subjectName;
        this.students = students;
    }

    public SubjectEntity(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectEntity() {

    }

    public Long getId() {
        return id;
    }

    public Set<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }
}
