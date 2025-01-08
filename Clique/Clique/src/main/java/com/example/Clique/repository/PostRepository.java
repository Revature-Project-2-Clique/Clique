package com.example.Clique.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {

    List<Posts> findAllByPosterId(Long posterId);

    @Query("SELECT p FROM Posts p WHERE LOWER(p.postText) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Posts> searchByContent(@Param("query") String query);

    Optional<List<Posts>> findByPosterIdInOrderByPostIdDesc(List<Long> posterIds);
}
