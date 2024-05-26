package com.example.freeteachbe.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int grade;
    @ManyToMany
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonIgnoreProperties("students")
    private Set<SubjectEntity> subjects;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @OneToMany(mappedBy = "student")
    private Set<ProblemPostEntity> problemPosts;

    public Set<ProblemPostEntity> getProblemPosts() {
        return problemPosts;
    }

    public void setProblemPosts(Set<ProblemPostEntity> problemPosts) {
        this.problemPosts = problemPosts;
    }

    public StudentEntity(int grade, Set<SubjectEntity> subjects, UserEntity user) {
        this.grade = grade;
        this.subjects = subjects;
        this.user = user;
    }

    public StudentEntity(int grade) {
        this.grade = grade;
    }

    public StudentEntity() {

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

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
