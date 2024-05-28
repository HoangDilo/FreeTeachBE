package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.DocumentPostDTO;
import com.example.freeteachbe.DTO.BodyPayload.TeacherDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.DocumentData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.FeedbackData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.TeacherData;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Service.DocumentService;
import com.example.freeteachbe.Service.FeedbackService;
import com.example.freeteachbe.Service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    @Operation(summary = "Lấy ra danh sách các gia sư của hệ thống")
    public List<TeacherData> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @PostMapping("/register")
    @Operation(summary = "Đăng ký một gia sư mới")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nhập vào các trường để đăng ký " +
            "gia sư: giá 1 giờ, mô tả bản thân, thời gian hoạt động (dạng hh:mm:ss), các ngày hoạt động trong tuần" +
            " (cách nhau bằng dấu phẩy), ID của 1 user đã đăng ký trong hệ thống, " +
            "danh sách các môn học chuyên môn")
    public ResponseEntity<Message> registerTeacher(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody TeacherDTO teacherDTO) {
        return teacherService.registerTeacher(user, teacherDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin của một gia sư bằng user id")
    @Parameter(name = "id", description = "Nhập vào user id của gia sư muốn lấy thông tin")
    public ResponseEntity<TeacherData> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("{id}/subjects")
    @Operation(summary = "Lấy ra danh sách câc môn học chuyên môn của một gia sư")
    public ResponseEntity<List<SubjectData>> getTeacherSubjects(
            @PathVariable Long id
    ) {
        return teacherService.getTeacherSubjects(id);
    }

    @GetMapping("/my/document")
    public ResponseEntity<Set<DocumentData>> getMyDocumentPost(
            @AuthenticationPrincipal UserEntity user
    ) {
        return ResponseEntity.ok(documentService.getMyDocumentPost(user));
    }

    @PostMapping("/my/document")
    public ResponseEntity<Message> createMyDocumentPost(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody DocumentPostDTO documentPostDTO
    ) {
        return documentService.createMyDocumentPost(user, documentPostDTO);
    }

    @PutMapping("/my/document/{id}")
    public ResponseEntity<Message> editMyDocument(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody DocumentPostDTO documentPostDTO,
            @PathVariable Long id
    ) {
        return documentService.editDocumentPost(user, id, documentPostDTO);
    }

    @DeleteMapping("/my/document/{id}")
    public ResponseEntity<Message> deleteMyDocument(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long id
    ) {
        return documentService.deleteDocumentPost(user, id);
    }

    @GetMapping("/{id}/document")
    public ResponseEntity<List<DocumentData>> getDocumentOfATeacher(
            @PathVariable Long id
    ) {
        return documentService.getDocumentOfATeacher(id);
    }

    @GetMapping("/my/feedback")
    public List<FeedbackData> getMyFeedback(
            @AuthenticationPrincipal UserEntity user
    ) {
        return feedbackService.getTeacherFeedback(user.getId());
    }
}
