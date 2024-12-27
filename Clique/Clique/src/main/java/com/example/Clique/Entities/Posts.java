package com.example.Clique.Entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Posts {

    private Long post_id;

    private Long poster_id;

    private String post_text;

    private String comment_text;

    private LocalDateTime posted_time;
}
