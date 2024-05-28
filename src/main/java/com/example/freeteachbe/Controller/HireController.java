package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Service.HireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hire")
@RequiredArgsConstructor
@Tag(name = "Hire")
public class HireController {
    private final HireService hireService;
    @PostMapping("/{teacherId}")
    public ResponseEntity<Message> hireATeacher(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long teacherId,
            @RequestParam int h
            ) {
        return hireService.hireATeacher(user, teacherId, h);
    }
}
