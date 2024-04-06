package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.BodyPayload.UserDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository ur;

    public List<UserEntity> getAllUser() {
        return ur.findAll();
    }
    public ResponseEntity<Message> createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity(userDTO.getName(), userDTO.getEmail(), userDTO.getAvatarURL(), userDTO.getUsername(), userDTO.getPassword());
        ur.save(user);
        return ResponseEntity.status(200).body(new Message("Thêm mới người dùng thành công"));
    }
}
