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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;

@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final UsersRepository usersRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public UserService(JwtUtil jwtUtil, UsersRepository usersRepository, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
    }
    
    public String registerUser(Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setIsPrivate(false);
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
        dto.setPrivate(user.getIsPrivate());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
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

    public String changeVisibility(Long userId){
        Optional<Users> usersOptional = usersRepository.findById(userId);
        if(usersOptional.isPresent()){
            Users user = usersOptional.get();
            user.setIsPrivate(!user.getIsPrivate());
            usersRepository.save(user);
            return "Visibility updated";
        }
        throw new RuntimeException("User not found");
    }

    public UsersDTO updateProfilePicture(Long userId, MultipartFile file) {
        Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());

                InputStream inputStream = file.getInputStream();
                amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
                
                String imageUrl = amazonS3.getUrl(bucketName, fileName).toString();
                user.setProfilePictureUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading profile picture to S3", e);
            }
        }

        usersRepository.save(user);
        return mapToDTO(user);
    }
}
