package com.example.Clique.controller;

import java.util.List;

import com.example.Clique.dto.UsersDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.Clique.Entities.Connections;
import com.example.Clique.service.ConnectionService;
import com.example.Clique.service.UserService;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;
    private final UserService userService;

    public ConnectionController(ConnectionService connectionService, UserService userService) {
        this.connectionService = connectionService;
        this.userService = userService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return userService.getUserByUsername(username).getUserId();
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

    @GetMapping("/{id}/following")
    public ResponseEntity<List<UsersDTO>> getFollowing(@PathVariable Long id) {
        return ResponseEntity.ok(connectionService.getFollowing(id));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UsersDTO>> getFollowers(@PathVariable Long id) {
        return ResponseEntity.ok(connectionService.getFollowers(id));
    }

    @GetMapping("/{followerId}/isFollowing/{followingId}")
    public ResponseEntity<Boolean> isUserFollowing(@PathVariable Long followerId, @PathVariable Long followingId) {
        boolean isFollowing = connectionService.isUserFollowing(followerId, followingId);
        return ResponseEntity.ok(isFollowing);
    }

}
