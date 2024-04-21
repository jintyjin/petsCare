package com.petsCare.petsCare.memory.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public interface MemoryException {

	public static final MemoryMakeException MEMORY_MAKE_EXCEPTION = new MemoryMakeException(ErrorMessage.EXCEPTION);
	public static final MemoryDuplicatedMemoryPathException MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION = new MemoryDuplicatedMemoryPathException(ErrorMessage.MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION);
}
