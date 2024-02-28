package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByUserId(Long userId);
}
