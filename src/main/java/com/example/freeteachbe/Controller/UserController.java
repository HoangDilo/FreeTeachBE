package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.*;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.RoleData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.UserData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.UserInfoData;
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
import java.util.Set;

@RestController
@Tag(name = "User")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private AuthService authService;

    @GetMapping()
    @Operation(summary = "(DEV) Lấy ra danh sách các user")
    public List<UserEntity> testUsers() {
        return us.getAllUser();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa một user bằng id")
    @Parameter(name = "id", description = "Nhập vào id của user muốn xóa")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return us.deleteUser(id);
    }

    @GetMapping("/role")
    @Operation(summary = "Lấy ra vai trò của một user", description = "Giá trị: \"student\": học sinh " +
            "| \"teacher\": gia sư " +
            "| \"null\": chưa đăng ký vai trò")
    public  ResponseEntity<RoleData> checkUserRole(@AuthenticationPrincipal UserEntity userEntity) {
        return us.checkUserRole(userEntity.getId());
    }

    @PutMapping("/change-password")
    @Operation(summary = "Đổi mật khẩu của 1 người dùng")
    public ResponseEntity<Message> changePassword(
            @AuthenticationPrincipal UserEntity userEntity,
            @RequestBody ChangePasswordDTO changePasswordDTO) {
        return us.changePassword(userEntity, changePasswordDTO.getOld_password(), changePasswordDTO.getNew_password());
    }

    @PutMapping("/change-avatar")
    public ResponseEntity<Message> changeAvatar(
            @AuthenticationPrincipal UserEntity userEntity,
            @RequestBody ChangeAvatarDTO avatarDTO) {
        return ResponseEntity.ok(us.changeAvatar(userEntity, avatarDTO.getNew_avatar_url()));
    }

    @PutMapping("/change-display-name")
    public ResponseEntity<Message> changeDisplayName(
            @AuthenticationPrincipal UserEntity userEntity,
            @RequestBody ChangeNameDTO nameDTO
            ) {
        return ResponseEntity.ok(us.changeDisplayName(userEntity, nameDTO.getName()));
    }

    @PutMapping("/change-email")
    public ResponseEntity<Message> changeEmail(
            @AuthenticationPrincipal UserEntity userEntity,
            @RequestBody ChangeEmailDTO emailDTO
            ) {
        return ResponseEntity.ok(us.changeEmail(userEntity, emailDTO.getEmail()));
    }

    @GetMapping("/info")
    public UserData getMyInfo(
            @AuthenticationPrincipal UserEntity user
    ) {
        return us.getUserInfo(user);
    }

    @GetMapping("info/subjects")
    public Set<SubjectData> getMySubjects(
            @AuthenticationPrincipal UserEntity user
    ) {
        return us.getMySubjects(user);
    }
}
