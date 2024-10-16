package com.pinsoft.intern.validation;

import com.pinsoft.intern.exception.CommentValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class CommentValidation {

    private static final int MAX_LENGTH = 150;

    public void validation(String comment, Double rating) {
        if (comment == null || comment.isEmpty()) {
            throw new CommentValidationException("Comment cannot be empty.");
        }
        if (rating == null || rating.isNaN()) {
            throw new CommentValidationException("Rating has not been provided.");
        }

        if (rating <= 0.0 || rating > 5.0) {
            throw new CommentValidationException("Rating must be between 0.5 and 5.0.");
        }

        if (comment.length() > MAX_LENGTH) {
            throw new CommentValidationException("Comment must be a maximum of " + MAX_LENGTH + " characters.");
        }
    }
}
