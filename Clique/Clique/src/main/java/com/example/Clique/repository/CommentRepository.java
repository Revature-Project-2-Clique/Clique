package com.example.Clique.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Clique.Entities.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long>{
    
}
