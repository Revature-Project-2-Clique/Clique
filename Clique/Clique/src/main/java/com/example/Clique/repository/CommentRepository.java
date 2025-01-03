package com.example.Clique.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>{
    List<Comments> findByPostId(Long postId);
}
