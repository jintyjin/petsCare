package com.petsCare.petsCare.form.pet;

import lombok.Data;

@Data
public class PetsForm {

	private Long id;

	private String name;

	public PetsForm(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
