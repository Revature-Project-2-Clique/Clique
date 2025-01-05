package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Clique.Entities.Connections;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.UserDTO;
import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.ConnectionService;

public class ConnectionServiceTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ConnectionService connectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFollowUser() {
        Long userId = 1L;
        Long whoToFollow = 2L;

        Connections connection = new Connections(userId, whoToFollow);

        when(connectionRepository.save(any(Connections.class))).thenReturn(connection);

        Connections result = connectionService.followUser(userId, whoToFollow);

        assertNotNull(result);
        assertEquals(userId, result.getFollowerId());
        assertEquals(whoToFollow, result.getFollowingId());
        verify(connectionRepository, times(1)).save(any(Connections.class));
    }



    @Test
    void testGetAllFollowing() {
        Long userId = 1L;

        Connections connection1 = new Connections(userId, 2L);
        Connections connection2 = new Connections(userId, 3L);

        when(connectionRepository.findAllByFollowingId(userId)).thenReturn(List.of(connection1, connection2));

        List<Connections> allFollowing = connectionService.getAllFollowing(userId);

        assertEquals(2, allFollowing.size());
        verify(connectionRepository, times(1)).findAllByFollowingId(userId);
    }
}
