package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Clique.Entities.Comments;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.CommentDTO;
import com.example.Clique.repository.CommentRepository;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.service.CommentService;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComment() {
        Long userId = 1L;
        Long postId = 10L;
        String content = "This is a test comment";

        Comments savedComment = new Comments();
        savedComment.setCommentId(100L);
        savedComment.setPosterId(userId);
        savedComment.setPostId(postId);
        savedComment.setCommentText(content);
        savedComment.setPostedTime(LocalDateTime.now());

        Users user = new Users();
        user.setUserId(userId);
        user.setUsername("testUser");

        when(commentRepository.save(any(Comments.class))).thenReturn(savedComment);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        CommentDTO result = commentService.createComment(userId, postId, content);

        assertNotNull(result);
        assertEquals(100L, result.getComment_id());
        assertEquals("testUser", result.getUsername());
        assertEquals("This is a test comment", result.getComment_text());

        verify(commentRepository, times(1)).save(any(Comments.class));
        verify(userRepository, times(1)).findById(userId);


    }

    @Test
    void testGetComments() {
        Long postId = 1L;

        Comments comment1 = new Comments();
        comment1.setCommentId(1L);
        comment1.setPosterId(1L);
        comment1.setPostId(postId);
        comment1.setCommentText("First comment");
        comment1.setPostedTime(LocalDateTime.now());

        Comments comment2 = new Comments();
        comment2.setCommentId(2L);
        comment2.setPosterId(2L);
        comment2.setPostId(postId);
        comment2.setCommentText("Second comment");
        comment2.setPostedTime(LocalDateTime.now());

        List<Comments> commentsList = Arrays.asList(comment1, comment2);

        Users user1 = new Users();
        user1.setUserId(1L);
        user1.setUsername("user1");

        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setUsername("user2");

        when(commentRepository.findByPostId(postId)).thenReturn(commentsList);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        Set<CommentDTO> result = commentService.getComments(postId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getUsername().equals("user1")));
        assertTrue(result.stream().anyMatch(dto -> dto.getUsername().equals("user2")));
    
        verify(commentRepository, times(1)).findByPostId(postId);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testDeleteComment_ExistingComment() {
        Long commentId = 1L;

        when(commentRepository.existsById(commentId)).thenReturn(true);

        String result = commentService.deleteComment(1L, commentId);

        assertEquals("Comment deleted", result);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void testDeleteComment_NonExistingComment() {
        Long commentId = 1L;

        when(commentRepository.existsById(commentId)).thenReturn(false);

        String result = commentService.deleteComment(1L, commentId);

        assertEquals("No such comment exist", result);
        verify(commentRepository, never()).deleteById(commentId);
    }
}
