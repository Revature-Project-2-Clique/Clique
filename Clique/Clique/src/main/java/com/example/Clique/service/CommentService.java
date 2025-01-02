package com.example.Clique.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Comments;
import com.example.Clique.repository.CommentRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UsersRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            UsersRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String createComment(Long userId, Long postId, String content) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createComment'");
    }

    public List<Comments> getComments(Long userId, Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComments'");
    }

    public String deleteComment(Long userId, Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }
}
