package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, String> {
}
