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
public class PetBreed {

	@Id
	@Column(name = "breed_id")
	private String breed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private PetType petType;

	@OneToMany(mappedBy = "petBreed")
	private List<Pet> pet = new ArrayList<>();

	public PetBreed(String breed, PetType petType) {
		this.breed = breed;
		this.petType = petType;
		this.petType.getPetBreedList().add(this);
	}
}
