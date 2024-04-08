package com.petsCare.petsCare.pet.exception;

public class PetCanNotFindException extends RuntimeException {

	public PetCanNotFindException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
