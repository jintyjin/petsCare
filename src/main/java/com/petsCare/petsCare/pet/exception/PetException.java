package com.petsCare.petsCare.pet.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;
import org.springframework.validation.BindingResult;

public class PetException extends RuntimeException {
	private BindingResult bindingResult;
	public static final PetException PET_BREED_CAN_NOT_FIND_EXCEPTION = new PetException(ErrorMessage.PET_BREED_CAN_NOT_FIND_EXCEPTION);
	public static final PetException PET_CAN_NOT_FIND_EXCEPTION = new PetException(ErrorMessage.PET_CAN_NOT_FIND_EXCEPTION);

	public PetException(String message) {
		super(message);
	}

	public void addBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return this.bindingResult;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
