package com.example.Clique.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserSearchDTO> searchUsers(String query) {
        return userRepository.searchByUsername(query).stream()
                .map(user -> new UserSearchDTO(user.getUserId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<PostSearchDTO> searchPosts(String query) {
        return postRepository.searchByContent(query).stream()
                .map(post -> {
                    Long userId = post.getPosterId();
                    String username = userRepository.getUsernameByUserId(userId);
                    System.out.println(
                            "Post ID: " + post.getPostId() + ", Likes: " + reactionRepository.countByPostId(post.getPostId()));
                    return new PostSearchDTO(post.getPostId(), post.getPostText(), post.getPostedTime(), username,
                            reactionRepository.countByPostId(post.getPostId()));
                })
                .collect(Collectors.toList());
    }
}
