package com.example.Clique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Reactions;

@Repository
public interface ReactionRepository extends JpaRepository<Reactions, Long> {

}
