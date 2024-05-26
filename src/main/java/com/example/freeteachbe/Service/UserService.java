package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.BodyPayload.UserDTO;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.RoleData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository ur;
    @Autowired
    StudentRepository sr;
    @Autowired
    TeacherRepository tr;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUser() {
        return ur.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    public ResponseEntity<Message> deleteUser(Long id) {
        Optional<UserEntity> userFound = ur.findById(id);
        if(userFound.isPresent()) {
            UserEntity user = userFound.get();
            ur.delete(user);
            return ResponseEntity.status(200).body(new Message("Xóa thành công"));
        } else {
            return ResponseEntity.status(404).body(new Message("Không tìm thấy người dùng này"));
        }
    }

    public ResponseEntity<RoleData> checkUserRole(Long id) {
        Optional<UserEntity> userEntityOptional = ur.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (sr.findByUser(userEntity).isPresent()) {
                return ResponseEntity.status(200).body(new RoleData("student"));
            } else if (tr.findByUser(userEntity).isPresent()) {
                return ResponseEntity.status(200).body(new RoleData("teacher"));
            }
            return ResponseEntity.status(200).body(new RoleData(null));
        }
        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<Message> changePassword(UserEntity userEntity, String oldPass, String newPass) {
            if (passwordEncoder.matches(oldPass, userEntity.getPassword())) {
                if (!oldPass.equals(newPass)) {
                    userEntity.setPassword(passwordEncoder.encode(newPass));
                    ur.save(userEntity);
                    return ResponseEntity.status(200).body(new Message("Đổi mật khẩu thành công"));
                }
                return ResponseEntity.status(400).body(new Message("Mật khẩu mới không được trùng với mật khẩu cũ"));
            }
            return ResponseEntity.status(400).body(new Message("Mật khẩu không đúng"));
    }

    public Message changeAvatar(UserEntity userEntity, String avatar_url) {
        userEntity.setAvatarURL(avatar_url);
        ur.save(userEntity);
        return new Message("Đổi avatar thành công");
    }

    public Message changeDisplayName(UserEntity userEntity, String name) {
        userEntity.setName(name);
        ur.save(userEntity);
        return new Message("Đổi tên hiển thị thành công");
    }

    public Message changeEmail(UserEntity user, String email) {
        user.setEmail(email);
        ur.save(user);
        return new Message("Đôi email thành công");
    }
}
