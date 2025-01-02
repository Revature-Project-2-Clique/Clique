package com.example.Clique.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNameDTO {
    private String username;
    private String firstName;
    private String lastName;
}
