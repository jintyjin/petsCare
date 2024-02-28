package com.petsCare.petsCare.repository.pet;

import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetBreedRepository extends JpaRepository<PetBreed, String> {

	List<PetBreed> findByPetTypeType(String petType);

	Optional<PetBreed> findByBreed(String breed);
}
