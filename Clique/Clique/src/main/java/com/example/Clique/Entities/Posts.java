package com.example.Clique.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long posterId;

    private String postText;

    //private String commentText;

    private LocalDateTime postedTime;

    private String imageUrl;
    
    private String videoUrl;

    public Posts(String postText) {
        this.postText = postText;
    }

    public Posts(long l, String string, long m, LocalDateTime now) {
        this.postId = l;
        this.postText = string;
        this.posterId = m;
        this.postedTime = now;
    }
    
    public Posts(Long postId, String postText) {
        this.postId = postId;
        this.postText = postText;
    }
}
