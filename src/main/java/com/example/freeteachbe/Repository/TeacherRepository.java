package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    public Optional<TeacherEntity> findByUser(UserEntity userEntity);
    @Query("SELECT DISTINCT t FROM TeacherEntity t JOIN t.subjects s WHERE s.id = :subjectId")
    public List<TeacherEntity> findBySubject(@Param("subjectId") Long subjectId, Sort sort);
}
