package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.LikedDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Like;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/like")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<Like>> getAll() {
        List<Like> likes = likeService.findAll();
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> get(@PathVariable int id) {
       Like like = likeService.find(id);
       return ResponseEntity.ok(like);
    }

    @GetMapping("/blog/{blogId}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable int blogId) {
        List<User> users = likeService.findUsers(blogId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<List<Blog>> getBlogs(@PathVariable int userId) {
        List<Blog> users = likeService.findBlogs(userId);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody LikedDTO likedDTO) {
        likeService.save(likedDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
