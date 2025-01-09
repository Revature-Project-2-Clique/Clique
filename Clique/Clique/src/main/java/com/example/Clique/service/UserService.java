package com.example.Clique.service;

import com.example.Clique.dto.BioDTO;
import com.example.Clique.dto.UpdateNameDTO;
import com.example.Clique.dto.UpdatePasswordDTO;
import com.example.Clique.dto.UsersDTO;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final UsersRepository usersRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(JwtUtil jwtUtil, UsersRepository usersRepository, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
    }
    
    public String registerUser(Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        usersRepository.save(user);
        return jwtUtil.generateToken(user.getUsername());
    }

    public String loginUser(Users user) throws AuthenticationException {
        if (user.getUsername() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return jwtUtil.generateToken(user.getUsername());
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username does not exist"));
    }

    public UsersDTO getUserById(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if(userOptional.isPresent()){
            Users user = userOptional.get();
            return mapToDTO(user);
        } else {
            throw new RuntimeException("Invalid user id");
        }
    }

    public UsersDTO updateName(UpdateNameDTO updateNameDTO) {
        Optional<Users> userOptional = usersRepository.findByUsername(updateNameDTO.getUsername());
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            existingUser.setFirstName(updateNameDTO.getFirstName());
            existingUser.setLastName(updateNameDTO.getLastName());
            Users updatedUser = usersRepository.save(existingUser);
            return mapToDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Optional<Users> userOptional = usersRepository.findByUsername(updatePasswordDTO.getUsername());
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if (bcrypt.matches(updatePasswordDTO.getPassword(), existingUser.getPassword())) {
                existingUser.setPassword(new BCryptPasswordEncoder().encode(updatePasswordDTO.getNewPassword()));
                Users updated = usersRepository.save(existingUser);
                return;
            } else {
                throw new RuntimeException("Password mismatch");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public UsersDTO mapToDTO(Users user) {
        UsersDTO dto = new UsersDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setUserId(user.getUserId());
        dto.setBio(user.getBio());
        return dto;
    }

    public UsersDTO makeLoginDTO(Users user) {
        Users loggedIn = getUserByUsername(user.getUsername());
        return mapToDTO(loggedIn);
    }

    public BioDTO updateBio(Long userId, BioDTO bio) throws IllegalAccessException {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new IllegalAccessException("User not found"));

        user.setBio(bio.getBio());
        
        usersRepository.save(user);

        return new BioDTO(user.getBio());
        
    }
}
