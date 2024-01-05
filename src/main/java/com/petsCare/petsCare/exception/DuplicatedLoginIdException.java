package com.petsCare.petsCare.exception;

public class DuplicatedLoginIdException extends RuntimeException {
    public DuplicatedLoginIdException(String message) {
        super(message);
    }
}
