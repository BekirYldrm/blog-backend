package com.pinsoft.intern.service;

import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.AuthorRepository;
import com.pinsoft.intern.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class LoginService {

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;



    public Author findByEmailAndPassword(String email, String password) {
        return authorRepository.findByEmailAndPassword(email, password);
    }


    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
