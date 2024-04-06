package com.petsCare.petsCare.weight;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.weight.dto.WeightForm;
import com.petsCare.petsCare.weight.entity.Weight;
import com.petsCare.petsCare.weight.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Locale.KOREAN;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeightService {

	private final WeightRepository weightRepository;
	private final JpaPetRepository petRepository;
	private final MessageSource messageSource;

	@Transactional
	public void measure(WeightForm weightForm) {
		PetCanNotFindException petCanNotFindException
				= new PetCanNotFindException(getMessage("validation.constraints.canNotFindPet.message"));

		Pet pet = petRepository.findById(weightForm.getId())
				.orElseThrow();

		weightRepository.save(new Weight(weightForm.getWeight(), weightForm.getDate(), pet));
	}

	private String getMessage(String message) {
		return messageSource.getMessage(message, null, KOREAN);
	}
}
