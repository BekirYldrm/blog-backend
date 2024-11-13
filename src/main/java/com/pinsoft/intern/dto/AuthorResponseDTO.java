package com.pinsoft.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDTO {

    private int id;

    private String firstName;

    private String lastName;

    private String image;

}
