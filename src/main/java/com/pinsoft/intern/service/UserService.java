package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.UserDTO;
import com.pinsoft.intern.entity.Role;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.UserRepository;
import com.pinsoft.intern.validation.EmailValidation;
import com.pinsoft.intern.validation.PasswordValidation;
import com.pinsoft.intern.validation.UsernameValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordValidation passwordValidation;
    private final UsernameValidation usernameValidation;
    private final EmailValidation emailValidation;
    private final RoleService roleService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User find(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        if (userDetails.isUserSelf(id) || userDetails.isAdmin()) {
            return user;
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");

    }

    public User save(UserDTO userDTO) {
        Role role = roleService.find(1);
        String email = userDTO.getEmail();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        usernameValidation.validation(username);
        emailValidation.validation(email);
        passwordValidation.validation(password);
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    public void updatePassword(String password, int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = find(id);
        if (userDetails.isUserSelf(id)) {
            String oldPassword = user.getPassword();
            passwordValidation.validation(password, oldPassword);
            user.setPassword(password);
            userRepository.update(user);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updateUsername(String username, int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = find(id);
        if (userDetails.isUserSelf(id)) {
            String oldUsername = user.getUsername();
            usernameValidation.validation(username, oldUsername);
            user.setUsername(username);
            userRepository.update(user);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updateEmail(String email, int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = find(id);
        if (userDetails.isUserSelf(id)) {
            String oldEmail = user.getEmail();
            emailValidation.validation(email, oldEmail);
            user.setEmail(email);
            userRepository.update(user);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void updateImage(String image, int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = find(id);
        if (userDetails.isUserSelf(id)) {
            user.setImage(image);
            userRepository.update(user);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }

    public void delete(int id) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = find(id);
        if (userDetails.isUserSelf(id) || userDetails.isAdmin()) {
            userRepository.delete(user);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }


}
