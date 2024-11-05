package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.AuthorLoginRequest;
import com.pinsoft.intern.dto.UserLoginRequest;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.exception.InvalidCredentialsException;
import com.pinsoft.intern.jwt.JwtUtil;
import com.pinsoft.intern.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/author")
    public String authorLogin(@RequestBody AuthorLoginRequest request) {
        Author author = loginService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (author != null) {
            return jwtUtil.createToken(author.getEmail());
        }
        throw new InvalidCredentialsException("Invalid author credentials");
    }

    @PostMapping("/user")
    public String userLogin(@RequestBody UserLoginRequest request) {
        User user = loginService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user != null) {
            return jwtUtil.createToken(user.getUsername());
        }
        throw new InvalidCredentialsException("Invalid user credentials");
    }
}
