package com.petsCare.petsCare.pet.exception;

public class PetLeaveCanNotAfterTodayException extends RuntimeException {

	public PetLeaveCanNotAfterTodayException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
