package com.pinsoft.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    private String title;
    private String content;
    private String image;
    private int authorId;
    private int categoryId;
}
