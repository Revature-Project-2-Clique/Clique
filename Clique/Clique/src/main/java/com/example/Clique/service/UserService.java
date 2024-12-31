package com.example.Clique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class UserService {

    private JwtUtil jwtUtil;

    private UsersRepository usersRepository;

    @Autowired
    public UserService(JwtUtil jwtUtil, UsersRepository usersRepository) {
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
    }
    
    public String registerUser(Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        usersRepository.save(user);
        return jwtUtil.generateToken(user.getUsername());
    }
}
