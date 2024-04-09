package com.petsCare.petsCare.memory.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemoryWalkRequestForm {

	private Long petId;

	private LocalDate startTime;

	private LocalDate endTime;
}
