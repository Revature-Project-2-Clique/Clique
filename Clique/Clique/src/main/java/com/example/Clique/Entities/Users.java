package com.example.Clique.Entities;

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

    private boolean isPrivate = false; // Profiles are public by default

    @Column(nullable = true)
    private String profilePictureUrl;

    @Column(unique = true)
    private String username;

    @Column(nullable = true)
    private String bio;

    public Users() {

    }


    public Users (String firstName, String lastName, String email, String password, String username, String bio, boolean isPrivate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.isPrivate = isPrivate;
    }

    public void setIsPrivate(boolean b) {
        this.isPrivate = b;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
