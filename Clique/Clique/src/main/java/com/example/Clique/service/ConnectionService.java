package com.example.Clique.service;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;

@Service
public class ConnectionService {

    private ConnectionRepository connectionRepository;
    private UsersRepository userRepository;
    private JwtUtil jwtUtil;


    public ConnectionService(JwtUtil jwtUtil, ConnectionRepository connectionRepository, UsersRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    public String followUser(Long userId, Long whoToFollow) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'followUser'");
    }

    public String unfollowUser(Long userId, Long whoToUnfollow) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unfollowUser'");
    }

    public Long getFollowCount(Long userId, Long usersCount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFollowCount'");
    }

    public Boolean checkIfFollowing(Long userId, Long userId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkIfFollowing'");
    }

}
