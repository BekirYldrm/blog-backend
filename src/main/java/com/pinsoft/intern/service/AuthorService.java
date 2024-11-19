package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.AuthorDTO;
import com.pinsoft.intern.dto.AuthorResponseDTO;
import com.pinsoft.intern.entity.Author;
import com.pinsoft.intern.entity.Role;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.AuthorRepository;
import com.pinsoft.intern.validation.EmailValidation;
import com.pinsoft.intern.validation.NameValidation;
import com.pinsoft.intern.validation.PasswordValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final EmailValidation emailValidation;
    private final PasswordValidation passwordValidation;
    private final NameValidation nameValidation;
    private final RoleService roleService;

    public List<AuthorResponseDTO> findAll() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(author -> new AuthorResponseDTO(
                        author.getId(), author.getFirstName(), author.getLastName(), author.getImage()))
                .collect(Collectors.toList());

    }

    public Author find(int id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        return author;
    }

    public Author save(AuthorDTO authorDTO) {
        Role role = roleService.find(2);
        String email = authorDTO.getEmail();
        String password = authorDTO.getPassword();
        String firstName = authorDTO.getFirstName();
        String lastName = authorDTO.getLastName();
        nameValidation.validation(firstName);
        nameValidation.validation(lastName);
        emailValidation.validation(email);
        passwordValidation.validation(password);
        Author author = new Author();
        author.setEmail(email);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setPassword(password);
        author.setRole(role);
        return authorRepository.save(author);
    }

    public void delete(int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Author author = find(id);
        if (userDetails.isAuthorSelf(id) || userDetails.isAdmin()) {
            authorRepository.delete(author);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updateEmail(String email, int authorId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Author author = find(authorId);
        if (userDetails.isAuthorSelf(authorId)) {
            String oldEmail = author.getEmail();
            emailValidation.validation(email, oldEmail);
            author.setEmail(email);
            authorRepository.update(author);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updatePassword(String password, int authorId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Author author = find(authorId);
        if (userDetails.isAuthorSelf(authorId)) {
            String oldPassword = author.getPassword();
            passwordValidation.validation(password, oldPassword);
            author.setPassword(password);
            authorRepository.update(author);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updateImage(String image, int authorId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Author author = find(authorId);
        if (userDetails.isAuthorSelf(authorId)) {
            author.setImage(image);
            authorRepository.update(author);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public AuthorResponseDTO findByBlog(int id) {
        Author author = authorRepository.findByBlog(id);
        AuthorResponseDTO dto = new AuthorResponseDTO(author.getId(), author.getFirstName(), author.getLastName(), author.getImage());
        return dto;
    }
}
