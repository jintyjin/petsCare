package com.petsCare.petsCare.memory.dto.form;

import java.time.LocalDate;

public class MemoryWalkRequestForm extends MemoryWalkAbstractRequest {
	public MemoryWalkRequestForm(Long petId) {
		super(petId, LocalDate.now(), LocalDate.now());
	}

	public MemoryWalkRequestForm(Long petId, LocalDate startTime, LocalDate endTime) {
		super(petId, startTime, endTime);
	}
}
