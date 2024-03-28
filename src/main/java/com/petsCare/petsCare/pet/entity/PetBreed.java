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

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "breed_id")
	private Long id;

	@Column(name = "breed_pet")
	private String breed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private PetType petType;

	@OneToMany(mappedBy = "petBreed", cascade = CascadeType.ALL)
	private List<Pet> pet = new ArrayList<>();

	public PetBreed(String breed, PetType petType) {
		this.breed = breed;
		this.petType = petType;
	}
}
