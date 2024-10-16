package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.BlogValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class BlogValidation {

    private static int TITLE_MAX = 100;

    public void validation(String title, String content) {

        if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
            throw new BlogValidationException("Field cannot be empty.");
        }
        if (title.length() > TITLE_MAX) {
            throw new BlogValidationException("Title is too long");
        }
    }
}
