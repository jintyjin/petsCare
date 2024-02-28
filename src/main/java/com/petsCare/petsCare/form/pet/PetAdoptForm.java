package com.petsCare.petsCare.form.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class PetAdoptForm {

	@NotBlank
	private String petName;

	private MultipartFile thumbnail;

	@NotBlank
	private String breed;

	@NotNull
	private int petGender;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate petBirth;

	public PetAdoptForm() {
	}

	public PetAdoptForm(String petName, MultipartFile thumbnail, String breed, int petGender, LocalDate petBirth) {
		this.petName = petName;
		this.thumbnail = thumbnail;
		this.breed = breed;
		this.petGender = petGender;
		this.petBirth = petBirth;
	}
}
