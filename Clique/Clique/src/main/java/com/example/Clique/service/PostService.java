package com.example.Clique.service;

import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

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
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public PostService(UsersRepository usersRepository, PostRepository postRepository, 
                       ConnectionRepository connectionRepository, 
                       CommentService commentService, ReactionRepository reactionRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
        this.connectionRepository = connectionRepository;
        this.commentService = commentService;
        this.reactionRepository = reactionRepository;
    }

    public PostDTO createPost(Long userId, String postText, MultipartFile image, MultipartFile video) {
        if (postText == null || postText.isEmpty() || postText.length() > 255) {
            return null;
        }
        Optional<Users> userOptional = usersRepository.findById(userId);
        if(userOptional.isEmpty()) {
            return null;
        }

        Posts post = new Posts();
        post.setPostText(postText);
        post.setPosterId(userId);
        post.setPostedTime(LocalDateTime.now());

        if(image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(image.getSize());
                metadata.setContentType(image.getContentType());

                InputStream inputStream = image.getInputStream();
                amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                );
                String imageUrl = amazonS3.getUrl(bucketName, fileName).toString();
                post.setImageUrl(imageUrl);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(video != null && !video.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + video.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(video.getSize());
                metadata.setContentType(video.getContentType());

                InputStream inputStream = video.getInputStream();
                System.out.println("Uploading video: " + fileName);
                amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                );
                String videoUrl = amazonS3.getUrl(bucketName, fileName).toString();
                post.setVideoUrl(videoUrl);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        Posts savedPost = postRepository.save(post);
        return mapToPostDTO(savedPost, userId);
    }

    public Posts updatePost(Posts post) {
        Optional<Posts> postOptional = postRepository.findById(post.getPostId());
        if (!postOptional.isPresent()) {
            throw new RuntimeException("No such post exist");
        }

        Posts p = postOptional.get();
        p.setPostText(post.getPostText());
        p.setPostedTime(LocalDateTime.now());
        return postRepository.save(p);
    }


    public String deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return "post deleted";
        }
        return "No such post exists";
    }

    public List<Posts> getAllPosts(Long userId) {
        return (List<Posts>) postRepository.findAll();
    }

    public List<Posts> getPostsByUsername(Long userId, Users username) {
        return (List<Posts>) postRepository.findAllByPosterIdOrderByPostIdDesc(userId);
    }

    public List<PostDTO> getPostsByPosterId(Long posterId) {
        List<PostDTO> dtos = new ArrayList<>();
        List<Posts> posts = postRepository.findAllByPosterIdOrderByPostIdDesc(posterId);
        for (Posts post : posts) {
            dtos.add(mapToPostDTO(post, posterId));
        }
        return dtos;
    }

    public List<PostDTO> getUserFeed(Long userId) {
        List<PostDTO> rv = new ArrayList<>();
        List<Long> posterIds = connectionRepository.findFollowingIdsByFollowerId(userId)
            .orElseThrow(() -> new RuntimeException("User with id " + userId + " does not exist"));
        posterIds.add(userId);
        List<Posts> posts = postRepository.findByPosterIdInOrderByPostIdDesc(posterIds)
            .orElseThrow(() -> new RuntimeException("Error getting posts"));
        for (Posts p : posts) {
            rv.add(mapToPostDTO(p, userId));
        }
        return rv;
    }

    public List<PostDTO> getExploreFeed(Long userId) {
        List<PostDTO> rv = new ArrayList<>();
        List<Posts> posts = postRepository.findAllByOrderByPostIdDesc();
        for (Posts p : posts) {
            rv.add(mapToPostDTO(p, userId));
        }
        return rv;
    }

    public PostDTO mapToPostDTO(Posts post, Long userId) {
        Users poster = usersRepository.findById(post.getPosterId())
                                      .orElseThrow(() -> new RuntimeException("Poster does not exist"));
    
        Optional<Reactions> reactions = reactionRepository.findByReactorIdAndPostId(userId, post.getPostId());
        Boolean hasLiked = reactions.isPresent();
        Set<CommentDTO> cdto = commentService.getComments(post.getPostId());
    
        PostDTO pdto = new PostDTO();
        pdto.setPostId(post.getPostId());
        pdto.setUserId(poster.getUserId());
        pdto.setUsername(poster.getUsername());
        pdto.setProfilePictureUrl(poster.getProfilePictureUrl());
        
        pdto.setPostText(post.getPostText());
        pdto.setPostedTime(post.getPostedTime());
        pdto.setLikes(reactionRepository.countByPostId(post.getPostId()));
        pdto.setHasLiked(hasLiked);
        pdto.setCdto(cdto);
        pdto.setImageUrl(post.getImageUrl());
        pdto.setVideoUrl(post.getVideoUrl());
        return pdto;
    }
    
}
