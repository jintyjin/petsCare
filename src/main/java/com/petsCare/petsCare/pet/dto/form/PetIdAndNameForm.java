package com.petsCare.petsCare.pet.dto.form;

import com.petsCare.petsCare.pet.entity.Pet;
import lombok.Data;

@Data
public class PetIdAndNameForm {

	private Long id;

	private String name;

	public PetIdAndNameForm(Pet pet) {
		this.id = pet.getId();
		this.name = pet.getPetName();
	}
}
