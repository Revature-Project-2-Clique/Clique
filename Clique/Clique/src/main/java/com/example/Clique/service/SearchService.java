package com.example.Clique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class SearchService {

    private UsersRepository userRepository;
    private PostRepository postRepository;
    private ReactionRepository reactionRepository;
    private JwtUtil jwtUtil;

    public SearchService(JwtUtil jwtUtil, UsersRepository userRepository, PostRepository postRepository,
            ReactionRepository reactionRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    public List searchUsers(Long userId, String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchUsers'");
    }

    public List searchPosts(Long userId, String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchPosts'");
    }
}
