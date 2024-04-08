package com.petsCare.petsCare.pet.exception;

public class PetBreedCanNotFindException extends RuntimeException {

	public PetBreedCanNotFindException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
