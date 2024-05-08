package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.LoginDTO;
import com.example.freeteachbe.DTO.BodyPayload.RegisterDTO;
import com.example.freeteachbe.DTO.ReturnPayload.DataMessage;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.IsFirstLoginData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.LoginData;
import com.example.freeteachbe.Entity.Role;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository ur;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Message> login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if (username.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Tên đăng nhập không được để trống"));
        }
        if (password.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu không được để trống"));
        }
        Optional<UserEntity> userEntityOptional = ur.findByUsername(loginDTO.getUsername());
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            if (user.getPassword().equals(password)) {
                HashMap<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("id", user.getId());
                String jwt = jwtService.generateToken(extraClaims, user);
                return ResponseEntity.ok(new DataMessage<>("Đăng nhập thành công", LoginData.builder()
                        .is_first_login(user.isFirstLogin())
                        .access_token(jwt)
                        .build())) ;
            }
        }
        return ResponseEntity.status(404).body(new Message("Không tìm thấy người dùng này"));
    }

    public ResponseEntity<Message> register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String repeatPassword = registerDTO.getRepeatPassword();
        if (username.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Tên người dùng không được bỏ trống"));
        }
        Optional<UserEntity> userFound = ur.findByUsername(username);
        if (userFound.isPresent()) {
            return ResponseEntity.status(400).body(new Message("Tên nguời dùng đã được sử dụng"));
        }
        if (password.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu không được bỏ trống"));
        }
        if (email.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Email không được bỏ trống"));
        }
        if (!password.equals(repeatPassword)) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu nhập lại không khớp"));
        }
        LocalTime time = LocalTime.now();
        UserEntity user = new UserEntity(
                "user_" + time.toString(),
                email,
                "",
                username,
                passwordEncoder.encode(password),
                null); //to do: check role
        ur.save(user);
        return ResponseEntity.status(200).body(new Message("Đăng ký người dùng thành công"));
    }

    public ResponseEntity<IsFirstLoginData> getIsFirstLogin(@PathVariable Long id) {
        Optional<UserEntity> userEntityOptional = ur.findById(id);
        if (userEntityOptional.isPresent()) {
            return ResponseEntity.status(200).body(new IsFirstLoginData(userEntityOptional.get().isFirstLogin()));
        }
        return ResponseEntity.status(404).body(null);
    }

    public boolean checkAuthorization(Long userId) {
        Optional<UserEntity> userEntityOptional = ur.findById(userId);
        return userEntityOptional.isPresent();
    }
}
