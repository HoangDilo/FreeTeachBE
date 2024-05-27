package com.example.freeteachbe.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int pricePerHour;
    @Column
    private String description;
    @Column
    private LocalTime activeTimeStart;
    @Column
    private LocalTime activeTimeEnd;
    @Column
    private String activeDays;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @Column
    private Set<SubjectEntity> subjects;
    @Column
    @OneToMany(mappedBy = "teacher")
    private Set<DocumentPostEntity> documentPosts;
    @Column
    @OneToMany(mappedBy = "teacher")
    private Set<AnswerEntity> answers;

    public TeacherEntity(int pricePerHour, String description, LocalTime activeTimeStart, LocalTime activeTimeEnd, String activeDays, Set<SubjectEntity> subjects, UserEntity user) {
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.activeTimeStart = activeTimeStart;
        this.activeTimeEnd = activeTimeEnd;
        this.activeDays = activeDays;
        this.user = user;
        this.subjects = subjects;
    }

    public Set<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerEntity> answers) {
        this.answers = answers;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TeacherEntity() {

    }

    public Long getId() {
        return id;
    }

    public Set<DocumentPostEntity> getDocumentPosts() {
        return documentPosts;
    }

    public void setDocumentPosts(Set<DocumentPostEntity> documentPosts) {
        this.documentPosts = documentPosts;
    }

    public void addDocumentPost(DocumentPostEntity documentPost) {
        this.documentPosts.add(documentPost);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getActiveTimeStart() {
        return activeTimeStart;
    }

    public void setActiveTimeStart(LocalTime activeTimeStart) {
        this.activeTimeStart = activeTimeStart;
    }

    public LocalTime getActiveTimeEnd() {
        return activeTimeEnd;
    }

    public void setActiveTimeEnd(LocalTime activeTimeEnd) {
        this.activeTimeEnd = activeTimeEnd;
    }

    public String getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(String activeDays) {
        this.activeDays = activeDays;
    }

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}
