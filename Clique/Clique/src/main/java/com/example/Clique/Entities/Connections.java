package com.example.Clique.Entities;

import lombok.Data;

@Data
public class Connections {

    private Long connection_id;

    private Long follower_id;

    private Long following_id;

}
