package com.example.Clique.controller;

import java.net.Authenticator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Users;
import com.example.Clique.service.PostService;
import com.example.Clique.service.UserService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    private Long getUserId(Authentication auth){
        String username = auth.getName();
        return userService.getUserByUsername(username).getUserId();
    }

    @PostMapping
    private ResponseEntity<Posts> createPost(Authentication auth, @RequestBody Posts post) {
        Long userId = getUserId(auth);
        Posts createdPost = postService.createPost(userId, post);
        return ResponseEntity.status(200).body(createdPost);
    }

    @GetMapping("/feed")
    private ResponseEntity<List<Posts>> getPosts(Authentication auth) {
        Long userId = getUserId(auth);
        return ResponseEntity.status(200).body(postService.getAllPosts(userId));
    }

    @GetMapping("/username")
    private ResponseEntity<List<Posts>> getPostsByUsername(Authentication auth, @RequestBody Users username) {
        Long userId = getUserId(auth);
        return ResponseEntity.status(200).body(postService.getPostsByUsername(userId, username));
    }

}
