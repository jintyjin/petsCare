package com.petsCare.petsCare.entity.pet;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@ManyToOne
	@JoinColumn(name = "pet_type_id")
	private PetType petType;

	public PetBreed(String breed, PetType petType) {
		this.breed = breed;
		this.petType = petType;
		this.petType.getPetBreedList().add(this);
	}
}
