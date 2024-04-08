package com.petsCare.petsCare.memory.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public class MemoryException extends RuntimeException {

	public static final MemoryException MEMORY_EXCEPTION = new MemoryException(ErrorMessage.MEMORY_EXCEPTION);
	public static final MemoryException MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION = new MemoryException(ErrorMessage.MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION);

	public MemoryException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
