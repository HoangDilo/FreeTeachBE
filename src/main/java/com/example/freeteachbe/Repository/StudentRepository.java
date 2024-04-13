package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    public Optional<StudentEntity> findByUser(UserEntity user);
}
