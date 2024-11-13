package com.vmsbackend.controller;

import com.vmsbackend.entity.User;
import com.vmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @GetMapping
    public String Hello(){
        return "Hello!";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    @GetMapping("/listAll")
    public List<User> listAll(){
        return userService.listAll();
    }

}
