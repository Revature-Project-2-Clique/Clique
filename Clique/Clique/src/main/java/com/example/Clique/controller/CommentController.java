package com.example.Clique.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clique.Entities.Comments;
import com.example.Clique.dto.CommentDTO;
import com.example.Clique.service.CommentService;
import com.example.Clique.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    private Long getUserId(Authentication auth) {
        String username = auth.getName();
        return userService.getUserByUsername(username).getUserId();
    }

    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(Authentication auth, @RequestBody Comments comment) {
        Long userId = getUserId(auth);
        CommentDTO cdto = commentService.createComment(userId, comment.getPostId(), comment.getCommentText());
        return ResponseEntity.ok(cdto);

    }

    @GetMapping("/feed")
    public ResponseEntity<Set<CommentDTO>> getComments(Authentication auth, @RequestBody Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(Authentication auth, @RequestBody Long commentId) {
        Long userId = getUserId(auth);
        return ResponseEntity.ok(commentService.deleteComment(userId, commentId));
    }
}
