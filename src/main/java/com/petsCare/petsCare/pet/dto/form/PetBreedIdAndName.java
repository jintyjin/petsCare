package com.petsCare.petsCare.pet.dto.form;

import com.petsCare.petsCare.pet.entity.PetBreed;
import lombok.Data;

@Data
public class PetBreedIdAndName {

	private Long id;

	private String breed;

	public PetBreedIdAndName(PetBreed petBreed) {
		this.id = petBreed.getId();
		this.breed = petBreed.getBreed();
	}
}
