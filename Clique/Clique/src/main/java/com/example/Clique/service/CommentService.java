package com.example.Clique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Comments;
import com.example.Clique.repository.CommentRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;

    public CommentService(JwtUtil jwtUtil, CommentRepository commentRepository, PostRepository postRepository,
            UsersRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String createComment(Long userId, Long postId, String content) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createComment'");
    }

    public List<Comments> getComments(Long userId, Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComments'");
    }

    public String deleteComment(Long userId, Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }
}
