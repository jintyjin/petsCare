package com.petsCare.petsCare.pet.exception;

public class PetBirthCanNotAfterTodayException extends RuntimeException {

	public PetBirthCanNotAfterTodayException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
