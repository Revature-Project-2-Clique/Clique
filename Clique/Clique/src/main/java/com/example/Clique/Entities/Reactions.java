package com.example.Clique.Entities;

import lombok.Data;

@Data
public class Reactions {

    private Long reactionId;

    private Long postId;

    private Long commentId;

    private Long reactorId;
    
    private ReactionType reactionType;


    
}
