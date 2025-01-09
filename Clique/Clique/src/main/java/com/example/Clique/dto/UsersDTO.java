package com.example.Clique.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isPrivate;
}
