package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.StudentDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.StudentData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Tag(name = "Student")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService stuSer;
    @GetMapping
    public List<StudentData> getAllUser() {
        return stuSer.getAllStudent();
    }
    @PostMapping
    public ResponseEntity<Message> createUser(@RequestBody StudentDTO studentDTO) {
        return stuSer.createStudent(studentDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentData> getStudentById(@PathVariable Long id) {
        return  stuSer.getStudentById(id);
    }
    @GetMapping("/{id}/subjects")
    public ResponseEntity<Set<SubjectData>> getStudentSubjects(@PathVariable Long id) {
        return stuSer.getStudentSubjects(id);
    }
}
