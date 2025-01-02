package com.example.Clique.service;

import java.util.List;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Users;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.security.JwtUtil;

public class PostService {

    private JwtUtil jwtUtil;

    private PostRepository postRepository;

    public PostService(JwtUtil jwtUtil, PostRepository postRepository){
        this.jwtUtil = jwtUtil;
        this.postRepository = postRepository;
    }

    public Posts createPost(Long userId, Posts post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPost'");
    }

    public List<Posts> getAllPosts(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPosts'");
    }

    public List<Posts> getPostsByUsername(Long userId, Users username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPostsByUsername'");
    }
}
