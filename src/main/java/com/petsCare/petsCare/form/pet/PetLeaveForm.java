package com.petsCare.petsCare.form.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PetLeaveForm {

	private Long petId;

	@NotBlank
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate petLeaveDate;

	public PetLeaveForm(Long petId, LocalDate petLeaveDate) {
		this.petId = petId;
		this.petLeaveDate = petLeaveDate;
	}
}
