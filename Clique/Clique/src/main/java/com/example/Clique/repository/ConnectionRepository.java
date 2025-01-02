package com.example.Clique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Connections;

@Repository
public interface ConnectionRepository extends JpaRepository<Connections, Long> {

}
