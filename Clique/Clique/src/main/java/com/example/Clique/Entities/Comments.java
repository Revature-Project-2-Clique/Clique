package com.example.Clique.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "poster_id")
    private Long posterId;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "posted_time")
    private LocalDateTime postedTime;
}
