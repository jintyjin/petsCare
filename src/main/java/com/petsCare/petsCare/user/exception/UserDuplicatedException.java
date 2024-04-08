package com.petsCare.petsCare.user.exception;

import org.springframework.validation.BindingResult;

public class UserDuplicatedException extends RuntimeException {

	public UserDuplicatedException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
