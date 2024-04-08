package com.petsCare.petsCare.pet.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public class PetException extends RuntimeException {
	private final ErrorMessage errorMessage;

	public static final PetException PET_BREED_CAN_NOT_FIND_EXCEPTION = new PetException(ErrorMessage.PET_BREED_CAN_NOT_FIND_EXCEPTION);
	public static final PetException PET_CAN_NOT_FIND_EXCEPTION = new PetException(ErrorMessage.PET_CAN_NOT_FIND_EXCEPTION);

	public PetException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
