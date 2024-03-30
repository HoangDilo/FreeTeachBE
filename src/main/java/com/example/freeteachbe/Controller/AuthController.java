package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.ControllerReturn.ReturnMessage;
import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.DTO.ServiceReturn.StatusAndMessage;
import com.example.freeteachbe.Service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ReturnMessage> login(@RequestBody LoginDTO loginDTO) {
        StatusAndMessage result = authService.login(loginDTO);
        return ResponseEntity.status(result.getStatus()).body(new ReturnMessage(result.getMessage()));
    }
}
