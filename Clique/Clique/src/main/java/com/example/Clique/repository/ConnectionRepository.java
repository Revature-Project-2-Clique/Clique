package com.example.Clique.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Connections;

@Repository
public interface ConnectionRepository extends JpaRepository<Connections, Long> {

    List<Connections> findAllByFollowingId(Long followingId);
    List<Connections> findAllByFollowerId(Long followerId);

    Connections findByConnectionId(Long connectionId);
}
