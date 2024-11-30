package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.AuthorCustomDTO;
import com.pinsoft.intern.dto.AuthorDTO;
import com.pinsoft.intern.dto.AuthorResponseDTO;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/authors")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAll() {
        List<AuthorResponseDTO> dtoList = authorService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorCustomDTO> get(@PathVariable int id) {
        AuthorCustomDTO authorCustomDTO =  authorService.findCustom(id);
        return ResponseEntity.ok(authorCustomDTO);
    }

    @GetMapping("/my-info/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Author author = authorService.findMyInfo(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<AuthorResponseDTO> getByEmail(@PathVariable String email) {
        AuthorResponseDTO dto = authorService.findByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorByBlog(@PathVariable int id) {
        AuthorResponseDTO dto = authorService.findByBlog(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody AuthorDTO authorDTO) {
        Author savedAuthor = authorService.save(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @PatchMapping("email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable int id,@RequestParam String email) {
        authorService.updateEmail(email, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable int id,@RequestParam String password) {
        authorService.updatePassword(password,id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("image/{id}")
    public ResponseEntity<String> updateImage(@PathVariable int id,@RequestParam String image) {
        authorService.updateImage(image,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
