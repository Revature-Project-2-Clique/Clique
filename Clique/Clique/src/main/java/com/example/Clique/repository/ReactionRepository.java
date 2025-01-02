package com.example.Clique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Clique.Entities.Reactions;

public interface ReactionRepository extends JpaRepository<Reactions, Long>{

    Optional<Reactions> findByUserIdAndPostId(Long userId, Long postId);

    Long countByPostId(Long postId);

}
