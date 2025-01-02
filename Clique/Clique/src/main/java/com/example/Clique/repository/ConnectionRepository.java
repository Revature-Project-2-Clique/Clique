package com.example.Clique.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Clique.Entities.Connections;

public interface ConnectionRepository extends JpaRepository<Connections, Long> {

}
