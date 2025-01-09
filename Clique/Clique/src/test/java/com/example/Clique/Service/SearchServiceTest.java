package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

    /* 
    @Test
    void testSearchUsers() {
        String query = "john";

        Users user1 = new Users();
        user1.setUserId(1L);
        user1.setUsername("john_doe");

        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setUsername("john_smith");

        when(usersRepository.searchByUsername(query)).thenReturn(List.of(user1, user2));

        List<UserSearchDTO> result = searchService.searchUsers(query);

        assertEquals(2, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("john_smith", result.get(1).getUsername());
        verify(usersRepository, times(1)).searchByUsername(query);
    }

    @Test
    void testSearchPosts() {
        String query = "test";

        Posts post1 = new Posts();
        post1.setPostId(1L);
        post1.setPostText("This is a test post");
        post1.setPostedTime(LocalDateTime.now());
        post1.setPosterId(1L);

        Posts post2 = new Posts();
        post2.setPostId(2L);
        post2.setPostText("Another test post");
        post2.setPostedTime(LocalDateTime.now());
        post2.setPosterId(2L);

        when(postRepository.searchByContent(query)).thenReturn(List.of(post1, post2));
        when(usersRepository.getUsernameByUserId(1L)).thenReturn("user1");
        when(usersRepository.getUsernameByUserId(2L)).thenReturn("user2");
        when(reactionRepository.countByPostId(1L)).thenReturn(5L);
        when(reactionRepository.countByPostId(2L)).thenReturn(3L);

        List<PostSearchDTO> result = searchService.searchPosts(query);

        assertEquals(2, result.size());
        assertEquals("This is a test post", result.get(0).getPostText());
        assertEquals("Another test post", result.get(1).getPostText());
        assertEquals(5L, result.get(0).getLikes());
        assertEquals(3L, result.get(1).getLikes());
        verify(postRepository, times(1)).searchByContent(query);
        verify(usersRepository, times(1)).getUsernameByUserId(1L);
        verify(usersRepository, times(1)).getUsernameByUserId(2L);
    }*/
}
