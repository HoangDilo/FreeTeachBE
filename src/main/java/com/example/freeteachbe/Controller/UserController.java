package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/usersad")
    public String testUsers() {
        return "Con cac";
    }


}
