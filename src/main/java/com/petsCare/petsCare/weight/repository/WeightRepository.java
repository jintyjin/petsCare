package com.petsCare.petsCare.weight.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.weight.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {

    List<Weight> findByPet(Pet pet);
}
