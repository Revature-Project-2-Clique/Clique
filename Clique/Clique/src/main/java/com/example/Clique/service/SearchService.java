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

    public List<UserSearchDTO> searchUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserSearchDTO(user.getUserId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<PostSearchDTO> searchPosts(String query) {
        //System.out.println("Searching Posts:");
        return postRepository.searchByContent(query).stream()
                .map(post -> {
                    //System.out.println("Made it inside the post Repo");

                    Long userId = post.getPosterId();
                    //System.out.printf("\nMade it to get posterId: "+ userId);
                    
                    Long likes = reactionRepository.countByPostId(post.getPostId());
                    //System.out.printf("\nMade it to get likes: "+ likes);

                    Users username = userRepository.findByUserId(userId);
                    String uname = (String) username.getUsername();
                    //System.out.printf("\nMade it to get username off userId: "+ uname);

                    

                    System.out.printf(
                            "\nPost ID: " + post.getPostId() + ", Likes: " + likes);

                    return new PostSearchDTO(post.getPostId(), post.getPostText(), post.getPostedTime(), uname,
                            reactionRepository.countByPostId(post.getPostId()));
                })
                .collect(Collectors.toList());
    }
}
