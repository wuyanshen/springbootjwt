package com.jwt.study.security_jwt.controller;

import com.jwt.study.security_jwt.entity.MyUser;
import com.jwt.study.security_jwt.reponsitory.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(description = "用户访问api接口")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/hello")
    @ApiOperation("用户测试接口")
    public String SayHello(){
        return "hello world!";
    }

    @PostMapping("/regist")
    @ApiOperation("用户注册接口")
    public String regist(@RequestBody MyUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "注册成功！";
    }


}
