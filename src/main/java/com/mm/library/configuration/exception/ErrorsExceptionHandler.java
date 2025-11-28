package com.mm.library.configuration.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorsExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handle404Error() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle400Error(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                fieldErrors.stream().map(DataError::new).toList()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handle400Error(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handle401Error() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handle401Error(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handle403Error() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle500Error(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Invalid argument: " + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointer(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A required value was missing.");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Illegal state: " + ex.getMessage());
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(
                ex.getConstraintViolations().stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .toList()
        );
    }

    private record DataError(String field, String message) {
        public DataError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }


}