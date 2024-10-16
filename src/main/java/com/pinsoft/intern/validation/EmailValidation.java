package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.EmailAlreadyInUseException;
import com.pinsoft.intern.exception.EmailMatchException;
import com.pinsoft.intern.exception.EmailValidationException;
import com.pinsoft.intern.repository.AuthorRepository;
import com.pinsoft.intern.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EmailValidation {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public void validation(String newEmail, String oldEmail) {
        isFormatEmail(newEmail);
        isEmailMatch(newEmail, oldEmail);
        isEmailRegistered(newEmail);
    }

    public void validation(String email) {
        isFormatEmail(email);
        isEmailRegistered(email);
    }


    private boolean isFormatEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (email == null || email.isEmpty()) {
            throw new EmailValidationException("Email cannot be empty.");
        }
        if (!validator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return true;
    }

    private void isEmailMatch(String newEmail, String oldEmail) {
        if (newEmail.equals(oldEmail)) {
            throw new EmailMatchException();
        }
    }


    private void isEmailRegistered(String email) {
        if (userRepository.findByEmail(email).isPresent() || authorRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyInUseException();
        }
    }
}
