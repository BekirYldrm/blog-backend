package com.pinsoft.intern.service;
import com.pinsoft.intern.entity.Role;
import com.pinsoft.intern.validation.EmailValidation;
import com.pinsoft.intern.dto.UserDTO;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.UserRepository;
import com.pinsoft.intern.validation.PasswordValidation;
import com.pinsoft.intern.validation.UsernameValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
        return user;
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

    public User updatePassword(String password, int id) {
        User user = find(id);
        String oldPassword = user.getPassword();

        passwordValidation.validation(password,oldPassword);
        user.setPassword(password);

        return userRepository.update(user);
    }

    public User updateUsername(String username, int id) {
        User user = find(id);
        String oldUsername = user.getUsername();

        usernameValidation.validation(username,oldUsername);
        user.setUsername(username);

        return userRepository.update(user);
    }

    public User updateEmail(String email, int id) {

        User user = find(id);
        String oldEmail = user.getEmail();

        emailValidation.validation(email,oldEmail);
        user.setEmail(email);

        return userRepository.update(user);
    }

    public void delete(int id) {
        User user = find(id);
        userRepository.delete(user);
    }










}
