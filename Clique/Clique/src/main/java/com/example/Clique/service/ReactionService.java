package com.example.Clique.service;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UsersRepository userRepository;

    public ReactionService(ReactionRepository reactionRepository, PostRepository postRepository,
            UsersRepository userRepository) {
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
