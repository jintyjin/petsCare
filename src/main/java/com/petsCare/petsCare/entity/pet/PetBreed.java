package com.petsCare.petsCare.entity.pet;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pet_breed_id")
	private Long id;

	@Column(name = "pet_breed")
	private String breed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_type_id")
	private PetType petType;

	@OneToMany(mappedBy = "petBreed")
	private List<Pet> pet = new ArrayList<>();

	public PetBreed(String breed, PetType petType) {
		this.breed = breed;
		this.petType = petType;
		this.petType.getPetBreedList().add(this);
	}
}
