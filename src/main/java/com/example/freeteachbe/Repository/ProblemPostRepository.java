package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.ProblemPostEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProblemPostRepository extends JpaRepository<ProblemPostEntity, Long> {

    @Query("SELECT p FROM ProblemPostEntity p WHERE p.subject.id IN :subjects")
    List<ProblemPostEntity> findBySubjects(@Param("subjects") Set<Long> subjectIds, Pageable pageable);
}
