package com.example.Clique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Clique.Entities.Users;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Users findByUserId(Long userId);
  
    Optional<Users> findById(Long id);

    @Query("SELECT u FROM Users u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Users> searchByUsername(@Param("query") String query);

    String getUsernameByUserId(Long userId);
}
