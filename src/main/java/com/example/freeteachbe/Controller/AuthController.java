package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.LoginDTO;
import com.example.freeteachbe.DTO.BodyPayload.RegisterDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.IsFirstLoginData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nhập vào tên người dùng và mật khẩu để đăng nhập")
    public ResponseEntity<Message> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("checkechk");
        return authService.login(loginDTO);
    }
    @PostMapping("/register")
    @Operation(summary = "Đăng ký người dùng mới")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nhập vào các trường để đăng ký " +
            "người dùng mới: tên người dùng, email, " +
            "mật khẩu, nhập lại mật khẩu")
    public ResponseEntity<Message> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
    @GetMapping("/check-first-login")
    @Operation(summary = "Kiểm tra một user xem đã đăng ký làm học sinh hoặc gia sư hay chưa",
    description = "Giá trị tham chiếu: true - người dùng chưa đăng ký làm học sinh hoặc gia sư | " +
            "false - người dùng đã đăng ký làm học sinh hoặc gia sư")
    public ResponseEntity<IsFirstLoginData> getIsFirstLogin (@AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(200).body(new IsFirstLoginData(userEntity.isFirstLogin()));
    }
}
