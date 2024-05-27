package com.example.freeteachbe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "problem_post")
public class ProblemPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private String image_url;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column
    @OneToMany(mappedBy = "post")
    private Set<AnswerEntity> answers;
    @Column
    private Long bestAnswerId;
}
