package com.example.Clique.repository;

import com.example.Clique.Entities.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {
    List<FollowRequest> findByTargetUserId(Long targetUserId);
    void deleteByTargetUserIdAndRequesterId(Long targetUserId, Long requesterId);
}
