package com.example.Clique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

}
