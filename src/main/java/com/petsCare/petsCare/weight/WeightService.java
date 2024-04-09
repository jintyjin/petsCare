package com.petsCare.petsCare.weight;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.exception.PetException;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.weight.dto.WeightForm;
import com.petsCare.petsCare.weight.entity.Weight;
import com.petsCare.petsCare.weight.repository.JpaWeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WeightService {

	private final JpaWeightRepository weightRepository;
	private final PetService petService;

	public WeightForm findPetWeights(Long petId) {
		Pet pet = weightRepository.findPetForWeights(petId);

		if (pet == null) {
			throw PetException.PET_CAN_NOT_FIND_EXCEPTION;
		}

		return new WeightForm(pet);
	}

	@Transactional
	public void measure(WeightForm weightForm) {
		Pet pet = petService.findPet(weightForm.getPetId());

		weightRepository.save(new Weight(weightForm.getWeight(), weightForm.getDate(), pet));
	}

	@Transactional
	public void deleteWeight(Long weightId) {
		weightRepository.deleteById(weightId);
	}
}
