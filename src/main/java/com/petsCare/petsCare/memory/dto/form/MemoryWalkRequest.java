package com.petsCare.petsCare.memory.dto.form;

import java.time.LocalDate;


public class MemoryWalkRequest extends MemoryWalkAbstractRequest {
	public MemoryWalkRequest(Long petId, LocalDate startTime, LocalDate endTime) {
		super(petId, startTime, endTime);
	}
}
