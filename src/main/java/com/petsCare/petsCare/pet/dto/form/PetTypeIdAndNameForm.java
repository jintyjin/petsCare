package com.petsCare.petsCare.pet.dto.form;

import com.petsCare.petsCare.pet.entity.PetType;
import lombok.Data;

@Data
public class PetTypeIdAndNameForm {

	private Long id;

	private String type;

	public PetTypeIdAndNameForm(PetType petType) {
		this.id = petType.getId();
		this.type = petType.getType();
	}
}
