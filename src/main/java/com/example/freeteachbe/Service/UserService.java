package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.LoginDTO;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository ur;

    public int loginService(LoginDTO loginDTO) {
//        Optional<> = ur.findByUsername(loginDTO.)
        return 1;
    }
}
