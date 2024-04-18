package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.LoginDTO;
import com.example.freeteachbe.DTO.BodyPayload.RegisterDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.IsFirstLoginData;
import com.example.freeteachbe.Repository.UserRepository;
import com.example.freeteachbe.Service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
    @GetMapping("{id}/check-first-login")
    public ResponseEntity<IsFirstLoginData> getIsFirstLogin (@PathVariable Long id) {
        return authService.getIsFirstLogin(id);
    }
}
