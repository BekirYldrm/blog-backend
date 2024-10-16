package com.pinsoft.intern.validation;

import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.exception.PasswordMatchException;
import com.pinsoft.intern.exception.PasswordValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PasswordValidation {
    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");


    public void validation(String newPassword, String oldPassword) {
        validatePassword(newPassword);
        isPasswordMatch(newPassword, oldPassword);

    }

    public void validation(String password) {

        validatePassword(password);
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_LENGTH) {
            throw new PasswordValidationException("Password length must be at least " + MIN_LENGTH + " characters");
        }
        if (!UPPER_CASE_PATTERN.matcher(password).find()) {
            throw new PasswordValidationException("Password must contain at least one uppercase letter");
        }
        if (!LOWER_CASE_PATTERN.matcher(password).find()) {
            throw new PasswordValidationException("Password must contain at least one lowercase letter");
        }
        if (!NUMBER_PATTERN.matcher(password).find()) {
            throw new PasswordValidationException("Password must contain at least one number");
        }
        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new PasswordValidationException("Password must contain at least one special character");
        }
    }

    private void isPasswordMatch(String newPassword, String oldPassword) {
        if (newPassword.equals(oldPassword)) {
            throw new PasswordMatchException();
        }
    }
}
