package com.pinsoft.intern.exceptionhandler;

import com.pinsoft.intern.exception.*;
import com.pinsoft.intern.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            {
                    EntityNotFoundException.class,
                    MethodArgumentTypeMismatchException.class,
                    IllegalArgumentException.class,
                    PasswordValidationException.class,
                    PasswordMatchException.class,
                    EmailMatchException.class,
                    EmailAlreadyInUseException.class,
                    EmailValidationException.class,
                    UsernameMatchException.class,
                    UsernameAlreadyInUseException.class,
                    UsernameValidationException.class,
                    NameValidationException.class,
                    BlogValidationException.class,
                    CommentValidationException.class,
            }
    )
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {

        HttpStatus status;

        if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof EmailAlreadyInUseException || e instanceof UsernameAlreadyInUseException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse error = new ErrorResponse(status.value(), e.getMessage());
        return new ResponseEntity<>(error, status);
    }


}
