package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
