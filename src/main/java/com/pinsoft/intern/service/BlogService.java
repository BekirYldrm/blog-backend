package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.BlogDTO;
import com.pinsoft.intern.dto.BlogUpdateDTO;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.repository.BlogRepository;
import com.pinsoft.intern.validation.BlogValidation;
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
    private final BlogValidation blogValidation;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog find(int id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found"));
        return blog;
    }

    public Blog save(BlogDTO blogDTO) {
        String title = blogDTO.getTitle();
        String content = blogDTO.getContent();
        String image = blogDTO.getImage();

        int authorId = blogDTO.getAuthorId();
        Author author = authorService.find(authorId);

        blogValidation.validation(title, content);

        Blog blog = new Blog();
        blog.setAuthor(author);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImage(image);
        blog.setDate(new Date());
        blog.setPopularity(0);
        return blogRepository.save(blog);
    }

    public Blog update(BlogUpdateDTO blogUpdateDTO, int id) {

        String title = blogUpdateDTO.getTitle();
        String content = blogUpdateDTO.getContent();
        String image = blogUpdateDTO.getImage();

        blogValidation.validation(title, content);

        Blog blog = find(id);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImage(image);
        return blogRepository.update(blog);
    }

    public Blog increasePopularity(Blog blog) {
        blog.setPopularity(blog.getPopularity() + 1);
        return blogRepository.update(blog);
    }

    public Blog decreasePopularity(Blog blog) {
        blog.setPopularity(blog.getPopularity() - 1);
        return blogRepository.update(blog);
    }

    public void delete(int id) {
        Blog blog = find(id);
        blogRepository.delete(blog);
    }

}
