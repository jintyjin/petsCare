package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.dto.form.PetDetailForm;
import com.petsCare.petsCare.pet.dto.form.PetsForm;

import java.util.List;

public interface PetRepository {

	List<PetsForm> showPets(Long userId);

	PetDetailForm showPetDetail(Long petId);
}
