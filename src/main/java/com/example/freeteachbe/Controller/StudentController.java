package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.StudentDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.StudentData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "(DEV) Lấy ra danh sách các student")
    @GetMapping
    public List<StudentData> getAllUser() {
        return stuSer.getAllStudent();
    }
    @Operation(summary = "Đăng ký mới một student", description = "Tạo mới 1 student với các tham số là: " +
            "lớp, userId, các subjectId mà student này đăng ký.")
    @PostMapping("/register")
    public ResponseEntity<Message> createUser(@RequestBody StudentDTO studentDTO) {
        return stuSer.createStudent(studentDTO);
    }
    @Operation(summary = "Lấy ra một student theo id")
    @Parameter(name = "id", description = "Truyền vào userId mà student này tham chiếu tới")
    @GetMapping("/{id}")
    public ResponseEntity<StudentData> getStudentById(@PathVariable Long id) {
        return  stuSer.getStudentById(id);
    }
    @Operation(summary = "Lấy ra danh sách các subject mà một student đã đăng ký")
    @Parameter(name = "id", description = "Truyền vào userId mà student này tham chiếu tới")
    @GetMapping("/{id}/subjects")
    public ResponseEntity<Set<SubjectData>> getStudentSubjects(@PathVariable Long id) {
        return stuSer.getStudentSubjects(id);
    }
}
