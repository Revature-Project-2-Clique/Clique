package com.example.Clique.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.service.ReactionService;
import com.example.Clique.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    private final ReactionService reactionService;
    private final UserService userService;

    public ReactionController(ReactionService reactionService, UserService userService) {
        this.reactionService = reactionService;
        this.userService = userService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return userService.getUserByUsername(username).getUserId();
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(Authentication auth, @RequestBody Long postId) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(reactionService.likePost(userId, postId));
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<String> unlikePost(Authentication auth, @RequestBody Long postId) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(reactionService.unlikePost(userId, postId));
    }

    @GetMapping("/get")
    public ResponseEntity<Long> getLikesCount(Authentication auth, @RequestBody Long postId) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(reactionService.getLikesCount(userId, postId));
    }

}
