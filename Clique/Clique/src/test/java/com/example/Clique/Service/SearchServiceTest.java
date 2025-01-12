package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Clique.Entities.Posts;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.PostSearchDTO;
import com.example.Clique.dto.UserSearchDTO;
import com.example.Clique.repository.PostRepository;
import com.example.Clique.repository.ReactionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.SearchService;

public class SearchServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchUsers_Success() {
        // Arrange
        Users user1 = new Users();
        user1.setUserId(1L);
        user1.setUsername("user1");

        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setUsername("user2");

        when(usersRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UserSearchDTO> result = searchService.searchUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void testSearchPosts_Success() {
        // Arrange
        Posts post1 = new Posts(1L, "Post 1", 1L, LocalDateTime.now());
        Posts post2 = new Posts(2L, "Post 2", 2L, LocalDateTime.now());

        Users user1 = new Users();
        user1.setUserId(1L);
        user1.setUsername("user1");

        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setUsername("user2");

        when(postRepository.searchByContent("Post")).thenReturn(Arrays.asList(post1, post2));
        when(usersRepository.findByUserId(1L)).thenReturn(user1);
        when(usersRepository.findByUserId(2L)).thenReturn(user2);
        when(reactionRepository.countByPostId(1L)).thenReturn(5L);
        when(reactionRepository.countByPostId(2L)).thenReturn(3L);

        // Act
        List<PostSearchDTO> result = searchService.searchPosts("Post");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getPostText());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals(5L, result.get(0).getLikes());
        verify(postRepository, times(1)).searchByContent("Post");
        verify(usersRepository, times(1)).findByUserId(1L);
        verify(usersRepository, times(1)).findByUserId(2L);
        verify(reactionRepository, times(2)).countByPostId(1L);
        verify(reactionRepository, times(2)).countByPostId(2L);
    }

    
}
