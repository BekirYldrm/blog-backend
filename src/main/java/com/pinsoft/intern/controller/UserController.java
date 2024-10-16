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
        List<User > users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        User user = userService.find(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
        User savedUser = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<User > updatePassword(@PathVariable int id , @RequestParam String password) {
        User updatedUser = userService.updatePassword(password,id);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/username/{id}")
    public ResponseEntity<User > updateUsername(@PathVariable int id , @RequestParam String username) {
        User updatedUser = userService.updateUsername(username,id);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<User > updateEmail(@PathVariable int id , @RequestParam String email) {
        User updatedUser = userService.updateEmail(email,id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
       userService.delete(id);
       return ResponseEntity.noContent().build();
    }
}
