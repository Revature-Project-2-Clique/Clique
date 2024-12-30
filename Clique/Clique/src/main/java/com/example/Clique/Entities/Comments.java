package com.example.Clique.Entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comments {

    private Long comment_id;

    private Long post_id;

    private Long poster_id;

    private String comment_text;

    private LocalDateTime posted_Time;
}
