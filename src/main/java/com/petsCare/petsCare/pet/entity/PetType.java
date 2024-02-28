package com.petsCare.petsCare.pet.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetType {

	@Id
	@Column(name = "pet_type")
	private String type;

	@OneToMany(mappedBy = "petType")
	private List<PetBreed> petBreedList = new ArrayList<>();

	public PetType(String type) {
		this.type = type;
	}
}
