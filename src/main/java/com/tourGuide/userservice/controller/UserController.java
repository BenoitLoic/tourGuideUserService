package com.tourGuide.userservice.controller;

import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.service.UserService;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // ajouter les controller pour le CRUD

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam String username) {

        return userService.getUserByUserName(username);
    }



}
