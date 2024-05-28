package com.example.freeteachbe.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String answer;
    @Column(length = 500)
    private String answerAvatarURL;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private ProblemPostEntity postProblem;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;
    @Column
    private LocalDateTime createdAt;
    public AnswerEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerAvatarURL() {
        return answerAvatarURL;
    }

    public void setAnswerAvatarURL(String answerAvatarURL) {
        this.answerAvatarURL = answerAvatarURL;
    }

    public ProblemPostEntity getPostProblem() {
        return postProblem;
    }

    public void setPostProblem(ProblemPostEntity postProblems) {
        this.postProblem = postProblems;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public AnswerEntity(String answer, String answerAvatarURL, ProblemPostEntity postProblem, TeacherEntity teacher, LocalDateTime createdAt) {
        this.answer = answer;
        this.answerAvatarURL = answerAvatarURL;
        this.postProblem = postProblem;
        this.teacher = teacher;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
