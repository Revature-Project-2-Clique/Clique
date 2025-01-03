package com.example.Clique.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.service.SearchService;
import com.example.Clique.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/search")
public class SearchController {

     private final SearchService searchService;
     private final UserService userService;

    public SearchController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return userService.getUserByUsername(username).getUserId();
    }

    @GetMapping("/users")
    public ResponseEntity<?> searchUsers(Authentication auth, @RequestParam String query) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(searchService.searchUsers(userId, query));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> searchPosts(Authentication auth, @RequestParam String query) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(searchService.searchPosts(userId, query));
    }

}
