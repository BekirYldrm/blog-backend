package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.UsernameAlreadyInUseException;
import com.pinsoft.intern.exception.UsernameMatchException;
import com.pinsoft.intern.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UsernameValidation {

    private UserRepository userRepository;

    public void validation(String username) {
        isUsernameRegistered(username);
    }

    public void validation(String newUsername, String oldUsername) {
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

}
