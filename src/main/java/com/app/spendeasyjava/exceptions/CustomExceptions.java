package com.app.spendeasyjava.exceptions;

import com.app.spendeasyjava.domain.responses.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.toString(), errorMessage.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationExceptions(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.toString(), errorMessage.toString()));
    }
}
