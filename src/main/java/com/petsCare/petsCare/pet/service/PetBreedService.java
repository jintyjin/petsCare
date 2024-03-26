package com.petsCare.petsCare.pet.service;

import com.petsCare.petsCare.pet.dto.form.PetBreedIdAndName;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetBreedService {

	private final PetBreedRepository petBreedRepository;

	public List<PetBreedIdAndName> showPetBreeds(Long petTypeId) {
		return petBreedRepository.findByPetTypeId(petTypeId)
				.stream()
				.map(petBreed -> new PetBreedIdAndName(petBreed))
				.toList();
	}
}
