package com.example.Clique.Entities;

import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String username;

    @Column(nullable = true)
    private String bio;

    public Users() {

    }


    public Users (String firstName, String lastName, String email, String password, String username, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.bio = bio;
    }
}
