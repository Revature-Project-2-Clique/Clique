package com.example.Clique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findPostById(Long id);

}
