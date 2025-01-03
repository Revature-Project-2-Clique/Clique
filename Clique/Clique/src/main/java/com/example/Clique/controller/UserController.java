package com.example.Clique.controller;

import com.example.Clique.Entities.Users;
import com.example.Clique.dto.UpdateNameDTO;
import com.example.Clique.dto.UpdatePasswordDTO;
import com.example.Clique.dto.UsersDTO;
import com.example.Clique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByUsername(username).getUserId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long id, Authentication auth) {
        try {
            UsersDTO usersDTO = userService.getUserById(id);
            return ResponseEntity.ok(usersDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/update-name")
    public ResponseEntity<UsersDTO> updateUserProfile(Authentication auth, @RequestBody UpdateNameDTO requestDTO) {
        return ResponseEntity.ok(userService.updateName(requestDTO));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(Authentication auth, @RequestBody UpdatePasswordDTO requestDTO) {
        try {
            userService.updatePassword(requestDTO);
            return ResponseEntity.ok().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
