package com.example.Clique.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Clique.Entities.Posts;
import com.example.Clique.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.dto.PostSearchDTO;
import com.example.Clique.dto.UserDTO;
import com.example.Clique.dto.UserSearchDTO;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class SearchService {

    private PostService postService;
    private UsersRepository userRepository;
    private PostRepository postRepository;
    private ReactionRepository reactionRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public SearchService(JwtUtil jwtUtil, UsersRepository userRepository, PostRepository postRepository,
            ReactionRepository reactionRepository, PostService postService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
        this.postService = postService;
    }

    public List<UserSearchDTO> searchUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserSearchDTO(user.getUserId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<PostDTO> searchPosts(String query, Long userId) {
        List<Posts> posts = postRepository.searchByContent(query);
        List<PostDTO> dtos = new ArrayList<>();
        for (Posts post : posts) {
            dtos.add(postService.mapToPostDTO(post, userId));
        }
        return dtos;

    }
}
