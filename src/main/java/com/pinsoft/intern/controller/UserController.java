package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.UserDTO;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        User user = userService.find(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/comment/{id}")
    public ResponseEntity<String> getUsernameByComment(@PathVariable int id) {
       String username = userService.findUsernameByComment(id);
        return ResponseEntity.ok(username);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
        User savedUser = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable int id, @RequestParam String password) {
        userService.updatePassword(password, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/username/{id}")
    public ResponseEntity<String> updateUsername(@PathVariable int id, @RequestParam String username) {
        userService.updateUsername(username, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable int id, @RequestParam String email) {
        userService.updateEmail(email, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/image/{id}")
    public ResponseEntity<String> updateImage(@PathVariable int id, @RequestParam String image) {
        userService.updateImage(image, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
