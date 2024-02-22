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
public class PetType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pet_type_id")
	private Long id;

	@Column(name = "pet_type")
	private String type;

	@OneToMany(mappedBy = "petType")
	private List<PetBreed> petBreedList = new ArrayList<>();

	public PetType(String type) {
		this.type = type;
	}
}
