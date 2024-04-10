package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.StudentDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Student")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository sr;
    @Autowired
    UserRepository ur;
    @GetMapping
    public List<StudentEntity> getAllStudent() {
        return sr.findAll();
    }
    @PostMapping
    public ResponseEntity<Message> createStudent(@RequestBody StudentDTO studentDTO) {
        Optional<UserEntity> userFound = ur.findById(studentDTO.getUser_id());
        if (userFound.isPresent()) {
            UserEntity user = userFound.get();
            StudentEntity student = new StudentEntity(studentDTO.getGrade());
            sr.save(student);
            return ResponseEntity.status(200).body(new Message("Thành công"));
        } else {
            return ResponseEntity.status(404).body(new Message("Không tìm thấy user"));
        }
    }
}
