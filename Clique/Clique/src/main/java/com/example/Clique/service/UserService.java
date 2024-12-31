package com.example.Clique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.security.JwtUtil;

@Service
public class UserService {

    private JwtUtil jwtUtil;

    @Autowired
    public UserService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    public String registerUser(Users user) {
        return jwtUtil.generateToken(user.getUsername());
    }
}
