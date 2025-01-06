package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Reactions;
import com.example.Clique.Entities.Users;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.ReactionService;

public class ReactionServiceTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ReactionService reactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePost_Success() {
        Long userId = 1L;
        Long postId = 1L;

        Posts post = new Posts();
        Users user = new Users();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reactionRepository.findByReactorIdAndPostId(userId, postId)).thenReturn(Optional.empty());

        String result = reactionService.likePost(userId, postId);

        assertEquals("Post liked successfully!", result);
        verify(reactionRepository, times(1)).save(any(Reactions.class));
    }

    @Test
    void testLikePost_AlreadyLiked() {
        Long userId = 1L;
        Long postId = 1L;

        Posts post = new Posts();
        Users user = new Users();
        Reactions reaction = new Reactions();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reactionRepository.findByReactorIdAndPostId(userId, postId)).thenReturn(Optional.of(reaction));

        String result = reactionService.likePost(userId, postId);

        assertEquals("You already liked this post!", result);
        verify(reactionRepository, never()).save(any(Reactions.class));
    }

    @Test
    void testLikePost_PostNotFound() {
        Long userId = 1L;
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> reactionService.likePost(userId, postId));
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void testUnlikePost_Success() {
        Long userId = 1L;
        Long postId = 1L;

        Posts post = new Posts();
        Users user = new Users();
        Reactions reaction = new Reactions();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reactionRepository.findByReactorIdAndPostId(userId, postId)).thenReturn(Optional.of(reaction));

        String result = reactionService.unlikePost(userId, postId);

        assertEquals("Post unliked successfully", result);
        verify(reactionRepository, times(1)).delete(reaction);
    }

    @Test
    void testUnlikePost_NotLiked() {
        Long userId = 1L;
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.of(new Posts()));
        when(usersRepository.findById(userId)).thenReturn(Optional.of(new Users()));
        when(reactionRepository.findByReactorIdAndPostId(userId, postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> reactionService.unlikePost(userId, postId));
        assertEquals("You have not liked this post!", exception.getMessage());
    }

    @Test
    void testGetLikesCount() {
        Long postId = 1L;
        when(reactionRepository.countByPostId(postId)).thenReturn(5L);

        Long count = reactionService.getLikesCount(1L, postId);

        assertEquals(5L, count);
        verify(reactionRepository, times(1)).countByPostId(postId);
    }
}
