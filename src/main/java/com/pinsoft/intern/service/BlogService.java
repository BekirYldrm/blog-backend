package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.BlogDTO;
import com.pinsoft.intern.dto.BlogUpdateDTO;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthorService authorService;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog find(int id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found"));
        return blog;
    }

    public Blog save(BlogDTO blogDTO) {
        Author author = authorService.find(blogDTO.getAuthorId());
        Blog blog = new Blog();
        blog.setAuthor(author);
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setImage(blogDTO.getImage());
        blog.setDate(new Date());
        blog.setPopularity(0);
        return blogRepository.save(blog);
    }

    public Blog update(BlogUpdateDTO blogUpdateDTO , int id) {
        Blog blog = find(id);
        blog.setTitle(blogUpdateDTO.getTitle());
        blog.setContent(blogUpdateDTO.getContent());
        blog.setImage(blogUpdateDTO.getImage());
        return blogRepository.update(blog);
    }


    public void delete(int id) {
        Blog blog = find(id);
        blogRepository.delete(blog);
    }


}
