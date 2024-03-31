package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.DTO.ServiceReturn.StatusAndMessage;
import com.example.freeteachbe.DTO.UserDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository ur;

    public List<UserEntity> getAllUser() {
        return ur.findAll();
    }
    public StatusAndMessage createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity(userDTO.getName(), userDTO.getEmail(), userDTO.getAvatarURL(), userDTO.getUsername(), userDTO.getPassword());
        ur.save(user);
        return new StatusAndMessage(200, "Thêm mới người dùng thành công");
    }
}
