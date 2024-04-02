package com.petsCare.petsCare.pet.service;
import com.petsCare.petsCare.memory.service.MemoryService;
import com.petsCare.petsCare.pet.dto.form.*;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.pet.exception.PetBreedCanNotFindException;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.user.exception.UserCanNotFindException;
import com.petsCare.petsCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

import static java.util.Locale.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

	private final JpaPetRepository jpaPetRepository;
	private final PetBreedRepository petBreedRepository;
	private final UserRepository userRepository;
	private final MessageSource messageSource;
	private final MemoryService memoryService;

	@Transactional
	public void adopt(PetAdoptForm petAdoptForm, UserDto userDto) {
		String petName = petAdoptForm.getPetName();
		Long breedId = petAdoptForm.getBreedId();
		int petGender = petAdoptForm.getPetGender();
		LocalDate petBirth = petAdoptForm.getPetBirth();

		UserCanNotFindException userCanNotFindException
				= new UserCanNotFindException(getMessage("validation.constraints.canNotFindUser.message"));

		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> userCanNotFindException);

		PetBreedCanNotFindException petBreedCanNotFindException
				= new PetBreedCanNotFindException(getMessage("validation.constraints.canNotFindBreed.message"));

		PetBreed petBreed = petBreedRepository.findById(breedId)
				.orElseThrow(() -> petBreedCanNotFindException);

		Pet pet = Pet.builder()
				.petName(petName)
				.petBreed(petBreed)
				.petGender(petGender)
				.bornTime(petBirth)
				.user(user)
				.build();

		jpaPetRepository.save(pet);

		if (!petAdoptForm.getThumbnail().isEmpty()) {
			pet.changeProfile(memoryService.saveMemory(List.of(petAdoptForm.getThumbnail()), userDto.getLoginId(), pet));
		}
	}

	public List<PetIdAndNameForm> showPets(Long id) {
		return jpaPetRepository.findByUserId(id)
				.stream()
				.map(pet -> new PetIdAndNameForm(pet))
				.toList();
	}

	public List<PetsForm> showPets(UserDto userDto) {
		return jpaPetRepository.showPets(userDto.getId());
	}

	public PetDetailForm showPetDetail(Long petId) {
		return jpaPetRepository.showPetDetail(petId);
	}

	@Transactional
	public void leave(PetDetailForm petDetailForm) {
		PetCanNotFindException petCanNotFindException
				= new PetCanNotFindException(getMessage("validation.constraints.canNotFindPet.message"));

		Pet pet = jpaPetRepository.findById(petDetailForm.getId())
				.orElseThrow(() -> petCanNotFindException);
		pet.leave(petDetailForm.getLeaveTime());
	}

	private String getMessage(String message) {
		return messageSource.getMessage(message, null, KOREAN);
	}
}
