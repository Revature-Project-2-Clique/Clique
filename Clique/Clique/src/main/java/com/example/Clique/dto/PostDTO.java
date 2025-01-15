package com.example.Clique.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long postId;
    private Long userId; 
    private String username;
    private String postText;
    private LocalDateTime postedTime;
    private Boolean hasLiked;
    private Long likes;
    private Set<CommentDTO> cdto;
    private String imageUrl;
    private String videoUrl;
}
