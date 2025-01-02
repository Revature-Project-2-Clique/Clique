package com.example.Clique.service;

import org.springframework.stereotype.Service;

import com.example.Clique.repository.ConnectionRepository;
import com.example.Clique.repository.UsersRepository;

@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UsersRepository userRepository;

    public ConnectionService(ConnectionRepository connectionRepository, UsersRepository userRepository) {
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
