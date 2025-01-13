package com.example.Clique.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long comment_id;
    private Long post_id;
    private Long userId; // Add this field
    private String username;
    private String comment_text;
    private LocalDateTime posted_Time;
}

