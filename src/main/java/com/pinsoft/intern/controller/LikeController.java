package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.LikedDTO;
import com.pinsoft.intern.entity.Like;
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

    @PostMapping
    public ResponseEntity<Like> create(@RequestBody LikedDTO likedDTO) {
        Like savedLike = likeService.save(likedDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLike);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        likeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
