package com.example.Clique.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserDTO> getFollowing(Long userId) {
        List<Connections> allFollowing = getAllFollowing(userId);
        List<UserDTO> allFollowingDTO = new ArrayList<>();
        for (Connections connections : allFollowing) {
            UserDTO newDTO = new UserDTO();
            Users newUser = userRepository.findByUserId(connections.getFollowingId());
            newDTO.setFirstName(newUser.getFirstName());
            newDTO.setLastName(newUser.getLastName());
            newDTO.setUserId(newUser.getUserId());
            newDTO.setUsername(newUser.getUsername());
            allFollowingDTO.add(newDTO);
        }
        return allFollowingDTO;
    }

    public Boolean checkIfFollowing(Long userId, Long userId2) {
        List<Connections> allFollowing = getAllFollowing(userId);
        if (allFollowing != null) {
            for (Connections connection : allFollowing) {
                if (connection.getFollowerId().equals(userId2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Connections> getAllFollowing(Long userId) {
        return connectionRepository.findAllByFollowingId(userId);
    }

}
