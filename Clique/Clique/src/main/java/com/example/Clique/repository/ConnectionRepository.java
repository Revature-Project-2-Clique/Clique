package com.example.Clique.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Connections;

@Repository
public interface ConnectionRepository extends JpaRepository<Connections, Long> {
    Connections findByFollowerIdAndFollowingId(Long followerId, Long followId);
    List<Connections> findAllByFollowingId(Long followingId);
    List<Connections> findAllByFollowerId(Long followerId);
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    Connections findByConnectionId(Long connectionId);

    @Query("SELECT c.followingId FROM Connections c WHERE c.followerId = :followerId")
    Optional<List<Long>> findFollowingIdsByFollowerId(@Param("followerId") Long followerId);
}
