package com.jwt.study.security_jwt.controller;

import com.jwt.study.security_jwt.entity.MyUser;
import com.jwt.study.security_jwt.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String SayHello(){
        return "hello world!";
    }

    @PostMapping("/regist")
    public void regist(@RequestBody MyUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
