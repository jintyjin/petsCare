package com.petsCare.petsCare.repository.pet;

import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {

	List<PetBreed> findByPetType(PetType petType);
}
