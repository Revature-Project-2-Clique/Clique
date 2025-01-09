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

    public List<PostDTO> getPostsByPosterId(Long posterId) {
        List<PostDTO> dtos = new ArrayList<>();
        List<Posts> posts = postRepository.findAllByPosterId(posterId);
        for (Posts post : posts) {
            dtos.add(mapToPostDTO(post, posterId));
        }
        return dtos;
    }

    public List<PostDTO> getUserFeed(Long userId) {
        List<PostDTO> rv = new ArrayList<>();
        List<Long> posterIds = connectionRepository.findFollowingIdsByFollowerId(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " does not exist"));
        List<Posts> posts = postRepository.findByPosterIdInOrderByPostIdDesc(posterIds).orElseThrow(() -> new RuntimeException("Error getting posts"));
        for (Posts p: posts) {
            rv.add(mapToPostDTO(p, userId));
        }
        return rv;
    }

    public List<PostDTO> getExploreFeed(Long userId) {
        List<PostDTO> rv = new ArrayList<>();
        List<Posts> posts = postRepository.findAll();
        for (Posts p: posts) {
            rv.add(mapToPostDTO(p, userId));
        }
        return rv;
    }

    public PostDTO mapToPostDTO(Posts post, Long userId) {
        Users u = usersRepository.findById(post.getPosterId()).orElseThrow(() -> new RuntimeException("User does not exist"));
        Users poster = usersRepository.findById(post.getPosterId()).orElseThrow(() -> new RuntimeException("Poster does not exist"));
        // if the logged in user whose id was passed to this method has reacted to this post or not
        Optional<Reactions> reactions = reactionRepository.findByReactorIdAndPostId(userId, post.getPostId());
        Boolean hasLiked = reactions.isPresent();
        Set<CommentDTO> cdto = commentService.getComments(post.getPostId());
        PostDTO pdto = new PostDTO();
        pdto.setPostId(post.getPostId());
        pdto.setUsername(poster.getUsername());
        pdto.setPostText(post.getPostText());
        pdto.setPostedTime(post.getPostedTime());
        pdto.setLikes(reactionRepository.countByPostId(post.getPostId()));
        pdto.setHasLiked(hasLiked);
        pdto.setCdto(cdto);

        return pdto;
    }
}
