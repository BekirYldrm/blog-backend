package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.CommentDTO;
import com.pinsoft.intern.entity.Comment;
import com.pinsoft.intern.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
@RestController
@Data

@AllArgsConstructor(onConstructor_ = @Autowired)
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        List<Comment> comments = commentService.findAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable int id) {
       Comment comment = commentService.find(id);
       return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Comment savedComment = commentService.save(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
       commentService.delete(id);
       return ResponseEntity.noContent().build();
    }
}