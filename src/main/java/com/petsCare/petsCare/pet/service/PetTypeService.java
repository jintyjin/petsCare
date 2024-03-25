package com.petsCare.petsCare.pet.service;

import com.petsCare.petsCare.pet.dto.form.PetTypeIdAndNameForm;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetTypeService {

	private final PetTypeRepository petTypeRepository;

	public List<PetTypeIdAndNameForm> showPetTypes() {
		return petTypeRepository.findAll()
				.stream()
				.map(petType -> new PetTypeIdAndNameForm(petType))
				.toList();
	}
}
