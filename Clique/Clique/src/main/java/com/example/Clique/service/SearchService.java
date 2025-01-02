package com.example.Clique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;

@Service
public class SearchService {

    private final UsersRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public SearchService(UsersRepository userRepository, PostRepository postRepository,
            ReactionRepository reactionRepository) {
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
