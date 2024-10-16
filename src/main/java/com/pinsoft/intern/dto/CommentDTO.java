package com.pinsoft.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String comment;
    private String rating;
    private int userId;
    private int blogId;
}
