package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserRepository ur;
    @GetMapping("/user")
    public List<UserEntity> testUsers() {
        return ur.findAll();
    }

}
