package com.petsCare.petsCare.weight;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.weight.dto.WeightForm;
import com.petsCare.petsCare.weight.entity.Weight;
import com.petsCare.petsCare.weight.repository.JpaWeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Locale.KOREAN;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WeightService {

	private final JpaWeightRepository weightRepository;
	private final PetService petService;
	private final MessageSource messageSource;

	public WeightForm findPetWeights(Long petId) {
		Pet pet = weightRepository.findPetForWeights(petId);

		if (pet == null) {
			throw new PetCanNotFindException(getMessage("validation.constraints.canNotFindPet.message"));
		}

		return new WeightForm(pet);
	}

	@Transactional
	public void measure(WeightForm weightForm) {
		Pet pet = petService.findPet(weightForm.getPetId());

		weightRepository.save(new Weight(weightForm.getWeight(), weightForm.getDate(), pet));
	}

	private String getMessage(String message) {
		return messageSource.getMessage(message, null, KOREAN);
	}
}
