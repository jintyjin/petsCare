package com.petsCare.petsCare.memory.exception;

public class MemoryMakeException extends RuntimeException {

	public MemoryMakeException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
