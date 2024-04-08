package com.petsCare.petsCare.memory.exception;

public class MemoryDuplicatedMemoryPathException extends RuntimeException {

	public MemoryDuplicatedMemoryPathException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
