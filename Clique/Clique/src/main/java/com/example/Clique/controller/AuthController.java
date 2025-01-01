package com.example.Clique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.Entities.Users;
import com.example.Clique.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<String> register(@RequestBody Users user) {
        String token = userService.registerUser(user);
        return ResponseEntity.status(200).header("Authorization", "Bearer "+token).body("User created");
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody Users user) {
        String token = userService.loginUser(user);
        return ResponseEntity.status(200).header("Authorization", "Bearer "+token).body("User logged in");
    }

}
