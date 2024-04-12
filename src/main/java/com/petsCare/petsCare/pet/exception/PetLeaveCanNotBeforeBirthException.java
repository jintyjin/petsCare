package com.petsCare.petsCare.pet.exception;

public class PetLeaveCanNotBeforeBirthException extends RuntimeException {
	public PetLeaveCanNotBeforeBirthException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
