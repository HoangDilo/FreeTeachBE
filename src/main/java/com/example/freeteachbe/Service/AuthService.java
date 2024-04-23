package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.LoginDTO;
import com.example.freeteachbe.DTO.BodyPayload.RegisterDTO;
import com.example.freeteachbe.DTO.ReturnPayload.DataMessage;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.IsFirstLoginData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.LoginData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository ur;

    public ResponseEntity<Message> login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Optional<UserEntity> userFound = ur.findByUsername(username);
        if (username.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Tên đăng nhập không được để trống"));
        }
        if (password.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu không được để trống"));
        }
        if (userFound.isPresent()) {
            UserEntity user = userFound.get();
            if (user.getPassword().equals(password)) {
                LoginData loginData = new LoginData(user.getId(), user.isFirstLogin());
                return ResponseEntity.status(200).body(new DataMessage<LoginData>("Đăng nhập thành công", loginData));
            }
        }
        return ResponseEntity.status(400).body(new Message("Tên người dùng hoặc mật khẩu sai"));
    }
    public ResponseEntity<Message> register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String repeatPassword = registerDTO.getRepeatPassword();
        if(username.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Tên người dùng không được bỏ trống"));
        }
        Optional<UserEntity> userFound = ur.findByUsername(username);
        if(userFound.isPresent()) {
            return ResponseEntity.status(400).body(new Message("Tên nguời dùng đã được sử dụng"));
        }
        if(password.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu không được bỏ trống"));
        }
        if(email.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Email không được bỏ trống"));
        }
        if(!password.equals(repeatPassword)) {
            return ResponseEntity.status(400).body(new Message("Mật khẩu nhập lại không khớp"));
        }
        LocalTime time = LocalTime.now();
        UserEntity user = new UserEntity("user_" + time.toString(), email, "", username, password);
        ur.save(user);
        return ResponseEntity.status(200).body(new Message("Đăng ký người dùng thành công"));
    }

    public ResponseEntity<IsFirstLoginData> getIsFirstLogin (@PathVariable Long id) {
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
