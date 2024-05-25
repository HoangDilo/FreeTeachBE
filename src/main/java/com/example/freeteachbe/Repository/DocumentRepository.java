package com.example.freeteachbe.Repository;

import com.example.freeteachbe.Entity.DocumentPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentPostEntity, Long> {

}
