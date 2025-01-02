package com.example.Clique.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Comments;
import com.example.Clique.repository.CommentRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public CommentService(JwtUtil jwtUtil, CommentRepository commentRepository, PostRepository postRepository,
            UsersRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String createComment(Long userId, Long postId, String content) {
        Comments comment = new Comments();
        comment.setPoster_id(userId);
        comment.setPost_id(postId);
        comment.setComment_text(content);
        comment.setPosted_Time(LocalDateTime.now());
        commentRepository.save(comment);
        return "comment added";
    }

    public List<Comments> getComments(Long userId, Long postId) {
        return commentRepository.findByPost_id(postId);
    }

    public String deleteComment(Long userId, Long commentId) {
        
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return "Comment deleted";
        }
        return "No such comment exist";
    }
}
