package com.pinsoft.intern.dto;

import com.pinsoft.intern.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCustomDTO {

    private int id;

    private String firstName;

    private String lastName;

    private String image;

    private List<Blog> myBlogs;

}
