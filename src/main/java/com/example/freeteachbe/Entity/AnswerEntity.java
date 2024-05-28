package com.example.freeteachbe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void setPostProblem(ProblemPostEntity postProblem) {
        this.postProblem = postProblem;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }
}
