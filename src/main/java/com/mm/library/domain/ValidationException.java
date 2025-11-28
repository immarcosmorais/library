package com.mm.library.domain;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
