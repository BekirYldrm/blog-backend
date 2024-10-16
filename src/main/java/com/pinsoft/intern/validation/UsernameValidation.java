package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.UsernameAlreadyInUseException;
import com.pinsoft.intern.exception.UsernameMatchException;
import com.pinsoft.intern.exception.UsernameValidationException;
import com.pinsoft.intern.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UsernameValidation {

    private UserRepository userRepository;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*$");

    public void validation(String username) {
        validateUsername(username);
        isUsernameRegistered(username);
    }

    public void validation(String newUsername, String oldUsername) {
        validateUsername(newUsername);
        isUsernameMatch(newUsername, oldUsername);
        isUsernameRegistered(newUsername);
    }

    private void isUsernameRegistered(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyInUseException();
        }
    }

    private void isUsernameMatch(String newUsername, String oldUsername) {
        if (newUsername.equals(oldUsername)) {
            throw new UsernameMatchException();
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new UsernameValidationException("Username cannot be empty.");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new UsernameValidationException("Username must be between 3 and 20 characters.");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new UsernameValidationException("Username must start with a letter and can only contain letters, numbers, and underscores (_).");
        }
    }

}
