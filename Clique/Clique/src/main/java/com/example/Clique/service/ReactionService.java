package com.example.Clique.service;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class ReactionService {

    private ReactionRepository reactionRepository;
    private PostRepository postRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;

    public ReactionService(JwtUtil jwtUtil, ReactionRepository reactionRepository, PostRepository postRepository,
            UsersRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String likePost(Long userId, Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likePost'");
    }

    public String unlikePost(Long userId, Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlikePost'");
    }

    public Long getLikesCount(Long userId, Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLikesCount'");
    }
}
