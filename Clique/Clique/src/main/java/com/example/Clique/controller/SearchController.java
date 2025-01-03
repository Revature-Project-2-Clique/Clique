package com.example.Clique.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.dto.PostSearchDTO;
import com.example.Clique.dto.UserSearchDTO;
import com.example.Clique.service.SearchService;
import com.example.Clique.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return UserService.getUserByUsername(username).getUserId();
    }

    @GetMapping("/users")
    public ResponseEntity<?> searchUsers(Authentication auth, @RequestParam String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                throw new IllegalArgumentException("Query parameter cannot be empty.");
            }
            Long userId = getUserId(auth);
            List<UserSearchDTO> users = searchService.searchUsers(query);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while searching for users.");
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> searchPosts(Authentication auth, @RequestParam String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                throw new IllegalArgumentException("Query parameter cannot be empty.");
            }
            Long userId = getUserId(auth);
            List<PostSearchDTO> users = searchService.searchPosts(query);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while searching for posts.");
        }
    }

}
