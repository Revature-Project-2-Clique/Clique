package com.example.Clique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {


}
