package com.pinsoft.intern.service;

import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.AuthorRepository;
import com.pinsoft.intern.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AuthorRepository authorRepository, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        Optional<Author> author = authorRepository.findByEmail(identifier);
        if (author.isPresent()) {
            return new CustomUserDetails(author.get().getId(), author.get().getEmail(), author.get().getPassword(), author.get().getRole().getRoleName());
        }

        Optional<User> user = userRepository.findByUsername(identifier);
        if (user.isPresent()) {
            return new CustomUserDetails(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getRole().getRoleName());
        }

        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }

    public static CustomUserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
