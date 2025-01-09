package com.example.Clique.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.Clique.Entities.FollowRequest;
import com.example.Clique.dto.UsersDTO;
import com.example.Clique.repository.FollowRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Connections;
import com.example.Clique.Entities.Users;
import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

import javax.management.Notification;

@Service
public class ConnectionService {

    private final FollowRequestRepository followRequestRepository;
    private ConnectionRepository connectionRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;

    public ConnectionService(JwtUtil jwtUtil, ConnectionRepository connectionRepository,
                             UsersRepository userRepository, FollowRequestRepository followRequestRepository) {
        this.jwtUtil = jwtUtil;
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.followRequestRepository = followRequestRepository;
    }

    public Connections followUser(Long followerId, Long whoToFollow) {
        Optional<Users> usersOptional = userRepository.findById(whoToFollow);

        if (followerId.equals(whoToFollow)) {
            throw new RuntimeException("Cannot follow self");
        }

        if (usersOptional.isPresent()) {
            if (connectionRepository.existsByFollowerIdAndFollowingId(followerId, whoToFollow)){
                throw new RuntimeException("Connection already exists");
            }
            Connections connections = new Connections(followerId, whoToFollow);
            return connectionRepository.save(connections);
        }
       return null;
    }

    public Integer unfollowUser(Long followerId, Long whoToUnfollow) {
        // if the connection exists, delete it
        if (connectionRepository.existsByFollowerIdAndFollowingId(followerId, whoToUnfollow)){
            Connections target = connectionRepository.findByFollowerIdAndFollowingId(followerId, whoToUnfollow);
            connectionRepository.delete(target);
            return 1;
        }

        return 0;
    }

    public List<UsersDTO> getFollowing(Long userId) {
        // connections where this user is the follower
        List<Connections> allFollowing = connectionRepository.findAllByFollowerId(userId);
        List<UsersDTO> allFollowingDTO = new ArrayList<>();
        for (Connections connections : allFollowing) {
            // map the followingId to a usersDTO
            allFollowingDTO.add(mapToUsersDTO(connections.getFollowingId()));
        }
        return allFollowingDTO;
    }

    public List<UsersDTO> getFollowers(Long userId) {
        // connections where this user is the following
        List<Connections> allFollowers = connectionRepository.findAllByFollowingId(userId);
        List<UsersDTO> allFollowersDTO = new ArrayList<>();
        for (Connections connections : allFollowers) {
            // map the followerId to a usersDTO
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

    public void sendFollowRequest(Long userId, Long targetUserId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        Optional<Users> targetUsersOptional = userRepository.findById(targetUserId);

        if (usersOptional.isPresent() && targetUsersOptional.isPresent()) {
            if (connectionRepository.existsByFollowerIdAndFollowingId(userId, targetUserId)){
                throw new RuntimeException("Connection already exists");
            }
            FollowRequest followRequest = new FollowRequest();
            followRequest.setRequesterId(userId);
            followRequest.setTargetUserId(targetUserId);
            followRequestRepository.save(followRequest);
        }

        else throw new RuntimeException("User not found");

    }

    public void approveFollowRequest(Long userId, Long requesterUserId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        Optional<Users> requestUsersOptional = userRepository.findById(requesterUserId);

        if (usersOptional.isPresent() && requestUsersOptional.isPresent()) {
            // Create the connection
            Connections connection = new Connections(requesterUserId, userId);
            connectionRepository.save(connection);

            // Delete the request
            followRequestRepository.deleteByTargetUserIdAndRequesterId(userId, requesterUserId);
        }

    }

    public List<FollowRequest> getFollowRequests(Long userId) {
        return followRequestRepository.findByTargetUserId(userId);
    }

    public void deleteFollowRequest(Long userId, Long requesterUserId) {
        followRequestRepository.deleteByTargetUserIdAndRequesterId(userId, requesterUserId);
    }

}
