package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    public Optional<SubjectEntity> findBySubjectName(String subjectName);
}
