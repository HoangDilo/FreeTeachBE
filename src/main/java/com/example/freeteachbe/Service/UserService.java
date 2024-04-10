package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.BodyPayload.UserDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Message> deleteUser(@RequestParam Long id) {
        Optional<UserEntity> userFound = ur.findById(id);
        if(userFound.isPresent()) {
            UserEntity user = userFound.get();
            ur.delete(user);
            return ResponseEntity.status(200).body(new Message("Xóa thành công"));
        } else {
            return ResponseEntity.status(404).body(new Message("Không tìm thấy người dùng này"));
        }
    }
}
