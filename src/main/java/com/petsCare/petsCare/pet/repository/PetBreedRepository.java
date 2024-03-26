package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.PetBreed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {

	List<PetBreed> findByPetTypeId(Long petTypeId);

	Optional<PetBreed> findByBreed(String breed);
}
