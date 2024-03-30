package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import com.example.freeteachbe.DTO.ServiceReturn.StatusAndMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository ur;

    public StatusAndMessage login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Optional<UserEntity> userFound = ur.findByUsername(username);
        if (username.isEmpty()) {
            return new StatusAndMessage(400, "Tên đăng nhập không được bỏ trống");
        }
        if (password.isEmpty()) {
            return new StatusAndMessage(400, "Mật khẩu không được bỏ trống");
        }
        if (userFound.isPresent()) {
            UserEntity user = userFound.get();
            if (user.getPassword().equals(password)) {
                return new StatusAndMessage(200, "Đăng nhập thành công");
            }
        }
        return new StatusAndMessage(400, "Tên người dùng hoặc mật khẩu sai");
    }
}
