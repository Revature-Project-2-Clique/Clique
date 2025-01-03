package com.example.Clique.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.ReactionType;
import com.example.Clique.Entities.Reactions;
import com.example.Clique.Entities.Users;
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

        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (reactionRepository.findByReactorIdAndPostId(userId, postId).isPresent()) {
            return "You already liked this post!";
        }

        Reactions reaction = new Reactions();
        reaction.setPostId(postId);
        reaction.setReactorId(userId);
        reaction.setReactionType(ReactionType.LIKE);

        reactionRepository.save(reaction);

        return "Post liked successfully!";
        
    }

    public String unlikePost(Long userId, Long postId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Reactions reaction = reactionRepository.findByReactorIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("You have not liked this post!"));

        reactionRepository.delete(reaction);

        return "Post unliked successfully";
    }

    public Long getLikesCount(Long userId, Long postId) {
        return reactionRepository.countByPostId(postId);
    }
}
