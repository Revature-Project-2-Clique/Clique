package com.example.Clique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Users;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class PostService {

    private JwtUtil jwtUtil;

    private UsersRepository usersRepository;
    private PostRepository postRepository;

    public PostService(JwtUtil jwtUtil, UsersRepository usersRepository, PostRepository postRepository){
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    public Posts createPost(Long userId, Posts post) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'createPost'");
        if (post.getPost_text().isEmpty() || post.getPost_text().length() > 255) {
            return null;
        }
        if (usersRepository.findById(userId).isEmpty()) {
            return null;
        }
        postRepository.save(post);
        return post;
    }

    public List<Posts> getAllPosts(Long userId) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getAllPosts'");
        return (List<Posts>) postRepository.findAll();
    }

    public List<Posts> getPostsByUsername(Long userId, Users username) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getPostsByUsername'");
        return (List<Posts>) postRepository.findAllByPosterId(userId);
    }
}
