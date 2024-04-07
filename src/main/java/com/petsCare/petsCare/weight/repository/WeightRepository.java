package com.petsCare.petsCare.weight.repository;

import com.petsCare.petsCare.pet.entity.Pet;

public interface WeightRepository {

	Pet findPetForWeights(Long petId);
}
