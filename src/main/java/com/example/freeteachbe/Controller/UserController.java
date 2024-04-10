package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.UserDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserService us;
    @GetMapping("/user")
    public List<UserEntity> testUsers() {
        return us.getAllUser();
    }
    @PostMapping("/user/create")
    public ResponseEntity<Message> createUser(@RequestBody UserDTO userDTO) {
        return us.createUser(userDTO);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Message> deleteUser(@RequestParam Long id) {
        return us.deleteUser(id);
    }
}
