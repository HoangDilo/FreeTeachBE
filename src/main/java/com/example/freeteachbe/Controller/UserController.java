package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.ControllerReturn.ReturnMessage;
import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.DTO.ServiceReturn.StatusAndMessage;
import com.example.freeteachbe.DTO.UserDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import com.example.freeteachbe.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ReturnMessage> createUser(@RequestBody UserDTO userDTO) {
        StatusAndMessage sm = us.createUser(userDTO);
        return ResponseEntity.status(sm.getStatus()).body(new ReturnMessage(sm.getMessage()));
    }
}
