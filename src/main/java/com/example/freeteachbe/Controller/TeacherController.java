package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.TeacherDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.TeacherData;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Service.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping
    public List<TeacherData> getAllTeacher() {
        return teacherService.getAllTeacher();
    }
    @PostMapping("/register")
    public ResponseEntity<Message> registerTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.registerTeacher(teacherDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherData> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }
    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectData>> getTeacherSubjects(@PathVariable Long id) {
        return teacherService.getTeacherSubjects(id);
    }
}
