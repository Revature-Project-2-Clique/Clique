package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.PostDTO;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.PostService;

public class PostServiceTest {
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost_ValidPost() {
        Long userId = 1L;
        Posts post = new Posts();
        post.setPostText("This is a valid post.");
        Users user = new Users();

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(post)).thenReturn(post);

        PostDTO createdPost = postService.createPost(userId, post);

        assertNotNull(createdPost);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testCreatePost_InvalidText() {
        Long userId = 1L;
        Posts post = new Posts();
        post.setPostText(""); 

        PostDTO createdPost = postService.createPost(userId, post);

        assertNull(createdPost);
        verify(postRepository, never()).save(any());
    }

    @Test
    void testCreatePost_UserNotFound() {
        Long userId = 1L;
        Posts post = new Posts();
        post.setPostText("This is a valid post.");

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        PostDTO createdPost = postService.createPost(userId, post);

        assertNull(createdPost);
        verify(postRepository, never()).save(any());
    }

    @Test
    void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(new Posts(), new Posts()));

        List<Posts> posts = postService.getAllPosts(1L);

        assertNotNull(posts);
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testGetPostsByUsername() {
        Long userId = 1L;
        Users user = new Users();
        when(postRepository.findAllByPosterId(userId)).thenReturn(List.of(new Posts(), new Posts()));

        List<Posts> posts = postService.getPostsByUsername(userId, user);

        assertNotNull(posts);
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findAllByPosterId(userId);
    }
}
