package com.example.Clique.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return UserService.getUserByUsername(username).getUser_id();
    }

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(Authentication auth, @RequestBody Long whoToFollow) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.followUser(userId, whoToFollow));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(Authentication auth, @RequestBody Long whoToUnfollow) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.unfollowUser(userId, whoToUnfollow));
    }

    @GetMapping("/getCount")
    public ResponseEntity<Long> getFollowCount(Authentication auth, @RequestParam Long usersCount) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.getFollowCount(userId, usersCount));
    }

    @GetMapping("/ifFollows")
    public ResponseEntity<Boolean> checkIfFollowing(Authentication auth, @RequestParam Long userId2) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(connectionService.checkIfFollowing(userId, userId2));
    }
    
    
}
