package com.example.Clique.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.Entities.Connections;
import com.example.Clique.dto.UserDTO;
import com.example.Clique.service.ConnectionService;
import com.example.Clique.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return UserService.getUserByUsername(username).getUserId();
    }

    @PostMapping("/follow")
    public ResponseEntity<Connections> followUser(Authentication auth, @RequestBody Long whoToFollow) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.followUser(userId, whoToFollow));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Integer> unfollowUser(Authentication auth, @RequestBody Long whoToUnfollow) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.unfollowUser(userId, whoToUnfollow));
    }

    @GetMapping("/getFollowing")
    public ResponseEntity<List<UserDTO>> getFollowing(Authentication auth) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.getFollowing(userId));
    }

    @GetMapping("/ifFollows")
    public ResponseEntity<Boolean> checkIfFollowing(Authentication auth, @RequestBody Long userId2) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.checkIfFollowing(userId, userId2));
    }

}
