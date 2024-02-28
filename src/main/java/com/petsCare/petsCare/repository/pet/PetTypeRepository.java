package com.petsCare.petsCare.repository.pet;

import com.petsCare.petsCare.entity.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, String> {
}
