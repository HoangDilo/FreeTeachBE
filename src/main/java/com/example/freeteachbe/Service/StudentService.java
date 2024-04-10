package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository sr;
}
