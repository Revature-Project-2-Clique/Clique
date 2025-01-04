package com.example.Clique.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.Clique.dto.UsersDTO;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Connections;
import com.example.Clique.Entities.Users;
import com.example.Clique.dto.UserDTO;
import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class ConnectionService {

    private ConnectionRepository connectionRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;

    public ConnectionService(JwtUtil jwtUtil, ConnectionRepository connectionRepository,
            UsersRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    public Connections followUser(Long userId, Long whoToFollow) {
        Connections connections = new Connections(userId, whoToFollow);
        return connectionRepository.save(connections);
    }

    public Integer unfollowUser(Long userId, Long whoToUnfollow) {
        List<Connections> allFollowing = getAllFollowing(userId);
        Long connectionId = null;
        for (Connections connections : allFollowing) {
            if (connections.getFollowingId() == whoToUnfollow) {
                connectionId = connections.getFollowingId();
            }
        }

        if (connectionId != null) {
            Connections target = connectionRepository.findByConnectionId(connectionId);
            connectionRepository.delete(target);
            return 1;
        }
        return 0;
    }

    public List<UsersDTO> getFollowing(Long userId) {
        List<Connections> allFollowing = getAllFollowing(userId);
        List<UsersDTO> allFollowingDTO = new ArrayList<>();
        for (Connections connections : allFollowing) {
            allFollowingDTO.add(mapToUsersDTO(connections.getFollowingId()));
        }
        return allFollowingDTO;
    }

    public List<UsersDTO> getFollowers(Long userId) {
        List<Connections> allFollowers = connectionRepository.findAllByFollowerId(userId);
        List<UsersDTO> allFollowersDTO = new ArrayList<>();
        for (Connections connections : allFollowers) {
            allFollowersDTO.add(mapToUsersDTO(connections.getFollowerId()));
        }
        return allFollowersDTO;
    }

    public Boolean isUserFollowing(Long followerId, Long followingId) {
        return connectionRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    public List<Connections> getAllFollowing(Long userId) {
        return connectionRepository.findAllByFollowingId(userId);
    }

    public UsersDTO mapToUsersDTO(Long userId) {
        UsersDTO usersDTO = new UsersDTO();
        Users user = userRepository.findByUserId(userId);
        usersDTO.setFirstName(user.getFirstName());
        usersDTO.setLastName(user.getLastName());
        usersDTO.setUserId(userId);
        usersDTO.setUsername(user.getUsername());
        return usersDTO;
    }
}
