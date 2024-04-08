package com.petsCare.petsCare.user.exception;

import org.springframework.validation.BindingResult;

public class UserCanNotFindException extends RuntimeException {


	public UserCanNotFindException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
