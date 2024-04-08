package com.petsCare.petsCare.memory.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public class MemoryException extends RuntimeException {

	private final ErrorMessage errorMessage;

	public static final MemoryException MEMORY_EXCEPTION = new MemoryException(ErrorMessage.MEMORY_EXCEPTION);
	public static final MemoryException MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION = new MemoryException(ErrorMessage.MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION);

	public MemoryException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
