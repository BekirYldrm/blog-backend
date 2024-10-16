package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.BlogDTO;
import com.pinsoft.intern.dto.BlogUpdateDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/blogs")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<List<Blog>> getAll() {
        List<Blog> blogs = blogService.findAll();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> get(@PathVariable int id) {
        Blog blog = blogService.find(id);
        return ResponseEntity.ok(blog);
    }

    @PostMapping
    public ResponseEntity<Blog> create(@RequestBody BlogDTO blogDTO) {
        Blog savedBlog = blogService.save(blogDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> update(@PathVariable int id, @RequestBody BlogUpdateDTO blogUpdateDTO) {
        Blog updatedBlog = blogService.update(blogUpdateDTO, id);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
      blogService.delete(id);
      return ResponseEntity.noContent().build();
    }
}
