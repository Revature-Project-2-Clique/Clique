package com.example.Clique.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    //Optional<Posts> findPostById(Long id);

    List<Posts> findAllByPosterId(Long poster_id);
}
