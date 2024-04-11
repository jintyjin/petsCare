package com.petsCare.petsCare.memory.dto.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class MemoryWalkAbstractRequest {

	private Long petId;

	private LocalDate startTime;

	private LocalDate endTime;

	public MemoryWalkAbstractRequest(Long petId, LocalDate startTime, LocalDate endTime) {
		this.petId = petId;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
