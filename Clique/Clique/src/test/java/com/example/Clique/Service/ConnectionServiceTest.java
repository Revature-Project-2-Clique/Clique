package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.example.Clique.Entities.Connections;
import com.example.Clique.Entities.FollowRequest;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.UserDTO;
import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.FollowRequestRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.ConnectionService;

public class ConnectionServiceTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private FollowRequestRepository followRequestRepository;

    @InjectMocks
    private ConnectionService connectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFollowUser_Success() {
        // Arrange
        Long followerId = 1L;
        Long whoToFollow = 2L;

        Users userToFollow = new Users();
        userToFollow.setUserId(whoToFollow);

        when(userRepository.findById(whoToFollow)).thenReturn(Optional.of(userToFollow));
        when(connectionRepository.existsByFollowerIdAndFollowingId(followerId, whoToFollow)).thenReturn(false);
        when(connectionRepository.save(any(Connections.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Connections result = connectionService.followUser(followerId, whoToFollow);

        // Assert
        assertNotNull(result);
        assertEquals(followerId, result.getFollowerId());
        assertEquals(whoToFollow, result.getFollowingId());

        // Verify interactions
        verify(userRepository, times(1)).findById(whoToFollow);
        verify(connectionRepository, times(1)).existsByFollowerIdAndFollowingId(followerId, whoToFollow);
        verify(connectionRepository, times(1)).save(any(Connections.class));
    }

    @Test
    void testUnfollowUser_Success() {
        // Arrange
        Long followerId = 1L;
        Long whoToUnfollow = 2L;

        Connections connection = new Connections();
        connection.setFollowerId(followerId);
        connection.setFollowingId(whoToUnfollow);

        when(connectionRepository.existsByFollowerIdAndFollowingId(followerId, whoToUnfollow)).thenReturn(true);
        when(connectionRepository.findByFollowerIdAndFollowingId(followerId, whoToUnfollow)).thenReturn(connection);

        // Act
        Integer result = connectionService.unfollowUser(followerId, whoToUnfollow);

        // Assert
        assertEquals(1, result);
        verify(connectionRepository, times(1)).existsByFollowerIdAndFollowingId(followerId, whoToUnfollow);
        verify(connectionRepository, times(1)).findByFollowerIdAndFollowingId(followerId, whoToUnfollow);
        verify(connectionRepository, times(1)).delete(connection);
    }

    @Test
    void testSendFollowRequest_Success() {
        // Arrange
        Long userId = 1L;
        Long targetUserId = 2L;

        Users user = new Users();
        user.setUserId(userId);

        Users targetUser = new Users();
        targetUser.setUserId(targetUserId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        when(connectionRepository.existsByFollowerIdAndFollowingId(userId, targetUserId)).thenReturn(false);
        when(followRequestRepository.existsByRequesterIdAndTargetUserId(userId, targetUserId)).thenReturn(false);

        // Act
        connectionService.sendFollowRequest(userId, targetUserId);

        // Assert
        verify(followRequestRepository, times(1)).save(any(FollowRequest.class));
    }

}
