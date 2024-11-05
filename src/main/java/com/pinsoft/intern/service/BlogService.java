package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.BlogDTO;
import com.pinsoft.intern.dto.BlogUpdateDTO;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Category;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.BlogRepository;
import com.pinsoft.intern.validation.BlogValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthorService authorService;
    private final BlogValidation blogValidation;
    private final CategoryService categoryService;

    public List<Blog> findAll(String sort) {

        if ("popularity".equalsIgnoreCase(sort)) {
            return blogRepository.findAllByOrderByPopularityDesc();
        } else if ("title".equalsIgnoreCase(sort)) {
            return blogRepository.findAllByOrderByTitleAsc();
        } else if ("date".equalsIgnoreCase(sort)) {
            return blogRepository.findAllByOrderByDateDesc();
        } else {
            return blogRepository.findAll();
        }
    }

    public Blog find(int id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found"));
        return blog;
    }

    public Blog save(BlogDTO blogDTO) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        int authorId = blogDTO.getAuthorId();
        int categoryId = blogDTO.getCategoryId();
        Author author = authorService.find(authorId);
        Category category = categoryService.find(categoryId);

        if (userDetails.isAuthorSelf(authorId)) {

            LocalDateTime localDateTime = LocalDateTime.now();
            String title = blogDTO.getTitle();
            String content = blogDTO.getContent();
            String image = blogDTO.getImage();
            blogValidation.validation(title, content);
            Blog blog = new Blog(title, content, localDateTime, 0, image, author, category);
            return blogRepository.save(blog);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public Blog update(BlogUpdateDTO blogUpdateDTO, int blogId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Blog blog = find(blogId);
        int authorId = blog.getAuthor().getId();

        if (userDetails.isAuthorSelf(authorId)) {
            String title = blogUpdateDTO.getTitle();
            String content = blogUpdateDTO.getContent();
            String image = blogUpdateDTO.getImage();
            blogValidation.validation(title, content);
            blog.setTitle(title);
            blog.setContent(content);
            blog.setImage(image);
            return blogRepository.update(blog);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public void delete(int blogId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Blog blog = find(blogId);
        int authorId = blog.getAuthor().getId();

        if (userDetails.isAdmin() || userDetails.isAuthorSelf(authorId)) {
            blogRepository.delete(blog);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void increasePopularity(Blog blog) {
        blog.setPopularity(blog.getPopularity() + 1);
        blogRepository.update(blog);
    }

    public void decreasePopularity(Blog blog) {
        blog.setPopularity(blog.getPopularity() - 1);
        blogRepository.update(blog);
    }
}
