package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPetRepository extends JpaRepository<Pet, Long>, PetRepository {

    List<Pet> findByUserId(Long userId);
}
