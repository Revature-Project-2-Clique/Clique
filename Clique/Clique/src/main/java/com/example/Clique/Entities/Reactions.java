package com.example.Clique.Entities;

import lombok.Data;

@Data
public class Reactions {

    private Long reaction_id;

    private Long post_id;

    private Long comment_id;

    private Long reactor_id;
}
