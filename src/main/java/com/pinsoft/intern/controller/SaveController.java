package com.pinsoft.intern.controller;


import com.pinsoft.intern.dto.SaveDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Save;
import com.pinsoft.intern.service.SaveService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/save")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class SaveController {

    private final SaveService saveService;

    @GetMapping
    public ResponseEntity<List<Save>> getAll() {
        List<Save> saveList = saveService.findAll();
        return ResponseEntity.ok(saveList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Save> get(@PathVariable int id) {
        Save save = saveService.find(id);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<List<Blog>> getBlogs(@PathVariable int userId) {
        List<Blog> users = saveService.findBlogs(userId);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Save> create(@RequestBody SaveDTO saveDTO) {
        Save save = saveService.save(saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        saveService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
