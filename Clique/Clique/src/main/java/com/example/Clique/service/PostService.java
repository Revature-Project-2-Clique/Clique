package com.example.Clique.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Reactions;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.CommentDTO;
import com.example.Clique.dto.PostDTO;
import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;

@Service
public class PostService {

    private UsersRepository usersRepository;
    private PostRepository postRepository;
    private ConnectionRepository connectionRepository;
    private ReactionRepository reactionRepository;
    private CommentService commentService;

    @Autowired
    public PostService(UsersRepository usersRepository, PostRepository postRepository, ConnectionRepository connectionRepository, CommentService commentService, ReactionRepository reactionRepository){
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
        this.connectionRepository = connectionRepository;
        this.commentService = commentService;
        this.reactionRepository = reactionRepository;
    }

    public Posts createPost(Long userId, Posts post) {
        if (post.getPostText().isEmpty() || post.getPostText().length() > 255) {
            return null;
        }
        if (usersRepository.findById(userId).isEmpty()) {
            return null;
        }
        post.setPosterId(userId);
        post.setPostedTime(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    public List<Posts> getAllPosts(Long userId) {
        return (List<Posts>) postRepository.findAll();
    }

    public List<Posts> getPostsByUsername(Long userId, Users username) {
        return (List<Posts>) postRepository.findAllByPosterId(userId);
    }

    public List<Posts> getPostsByPosterId(Long posterId) {
        return (List<Posts>) postRepository.findAllByPosterId(posterId);
    }

    public List<PostDTO> getUserFeed(Long userId) {
        List<PostDTO> rv = new ArrayList<>();
        List<Long> posterIds = connectionRepository.findFollowingIdsByFollowerId(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " does not exist"));
        List<Posts> posts = postRepository.findByPosterIdInOrderByPostIdDesc(posterIds).orElseThrow(() -> new RuntimeException("Error getting posts"));
        for (Posts p: posts) {
            Users u = usersRepository.findById(p.getPosterId()).orElseThrow(() -> new RuntimeException("User does not exist"));
            Optional<Reactions> reactions = reactionRepository.findByReactorIdAndPostId(userId, p.getPostId());
            Boolean hasLiked = reactions.isPresent() ? true : false;
            Set<CommentDTO> cdto = commentService.getComments(p.getPostId());
            PostDTO pdto = new PostDTO(p.getPostId(), u.getUsername(), p.getPostText(), p.getPostedTime(), hasLiked, reactionRepository.countByPostId(p.getPostId()), cdto);
            rv.add(pdto);
        }
        return rv;
    }
}
