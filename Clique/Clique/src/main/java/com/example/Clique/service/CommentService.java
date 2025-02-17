package com.example.Clique.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Comments;
import com.example.Clique.Entities.Users;
import com.example.Clique.repository.CommentRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.dto.CommentDTO;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UsersRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            UsersRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentDTO createComment(Long userId, Long postId, String content) {
        Comments comment = new Comments();
        comment.setPosterId(userId);
        comment.setPostId(postId);
        comment.setCommentText(content);
        comment.setPostedTime(LocalDateTime.now());
        Comments c = commentRepository.save(comment);
    
        Users u = userRepository.findById(c.getPosterId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
    
        return new CommentDTO(c.getCommentId(), c.getPostId(), u.getUserId(), u.getUsername(), c.getCommentText(), c.getPostedTime());
    }
    
    public Set<CommentDTO> getComments(Long postId) {
        List<Comments> commentList = commentRepository.findByPostId(postId);
        Set<CommentDTO> rv = new HashSet<>();
        for (Comments c : commentList) {
            Users u = userRepository.findById(c.getPosterId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));
            rv.add(new CommentDTO(c.getCommentId(), c.getPostId(), u.getUserId(), u.getUsername(), c.getCommentText(), c.getPostedTime()));
        }
        return rv;
    }
    

    public Comments updateComment(Long userId, Comments comment) {
        Comments c = commentRepository.findById(comment.getCommentId()).orElseThrow(() -> new RuntimeException("No such comment exists"));
        Comments rv = null;
        if (!comment.getCommentText().isEmpty()) {
            comment.setPosterId(userId);
            comment.setPostedTime(LocalDateTime.now());
            rv = commentRepository.save(comment);
        }
        return rv;
    }

    public String deleteComment(Long userId, Long commentId) {
        
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return "Comment deleted";
        }
        return "No such comment exist";
    }
}
