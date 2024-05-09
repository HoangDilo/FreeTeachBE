package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.ChangePasswordDTO;
import com.example.freeteachbe.DTO.BodyPayload.UserDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.RoleData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.AuthService;
import com.example.freeteachbe.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private AuthService authService;

    @GetMapping("/user")
    @Operation(summary = "(DEV) Lấy ra danh sách các user")
    public List<UserEntity> testUsers() {
        return us.getAllUser();
    }

    @PostMapping("/user/create")
    @Operation(summary = "(DEV) Tạo mới một user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nhập vào các trường thông tin cho 1 user")
    public ResponseEntity<Message> createUser(@RequestBody UserDTO userDTO) {
        return us.createUser(userDTO);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Xóa một user bằng id")
    @Parameter(name = "id", description = "Nhập vào id của user muốn xóa")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return us.deleteUser(id);
    }

    @GetMapping("/user/role")
    @Operation(summary = "Lấy ra vai trò của một user", description = "Giá trị: \"student\": học sinh " +
            "| \"teacher\": gia sư " +
            "| \"null\": chưa đăng ký vai trò")
    public  ResponseEntity<RoleData> checkUserRole(@AuthenticationPrincipal UserEntity userEntity) {
        return us.checkUserRole(userEntity.getId());
    }

    @PutMapping("/user/{id}/change-password")
    @Operation(summary = "Đổi mật khẩu của 1 người dùng")
    public ResponseEntity<Message> changePassword(@RequestHeader("authorization_fake") String authorization, @PathVariable Long id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        if (authService.checkAuthorization(Long.parseLong(authorization))) {
            return us.changePassword(id, changePasswordDTO.getOld_password(), changePasswordDTO.getNew_password());
        }
        return ResponseEntity.status(401).body(null);
    }
}
