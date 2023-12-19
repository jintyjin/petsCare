package com.petsCare.petsCare.repository.weight;

import com.petsCare.petsCare.entity.pet.Pet;
import com.petsCare.petsCare.entity.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {

    List<Weight> findByPet(Pet pet);
}
