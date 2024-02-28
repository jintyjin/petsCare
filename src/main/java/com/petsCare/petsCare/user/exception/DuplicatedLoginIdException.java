package com.petsCare.petsCare.user.exception;

public class DuplicatedLoginIdException extends RuntimeException {
    public DuplicatedLoginIdException(String message) {
        super(message);
    }
}
