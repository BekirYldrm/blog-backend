package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.NameValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor

public class NameValidation {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 30;

    public void validation(String name) {
        isNotEmpty(name);
        isValidFormat(name);
    }

    private void isNotEmpty(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NameValidationException("Name cannot be empty.");
        }
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            throw new NameValidationException("Name must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");
        }
    }

    private void isValidFormat(String name) {
        if (!name.matches("^[a-zA-ZçğışöüÇĞİŞÖÜ]+$")) {
            throw new NameValidationException("Name can only contain letters.");
        }
    }
}
