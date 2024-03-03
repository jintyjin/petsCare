package com.petsCare.petsCare.pet;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.pet.exception.PetBreedCanNotFindException;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.dto.form.PetAdoptForm;
import com.petsCare.petsCare.pet.dto.form.PetLeaveForm;
import com.petsCare.petsCare.pet.dto.form.PetsForm;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.PetRepository;
import com.petsCare.petsCare.user.exception.UserCanNotFindException;
import com.petsCare.petsCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final PetBreedRepository petBreedRepository;
	private final UserRepository userRepository;
	private final MessageSource messageSource;

	@Transactional
	public void adopt(PetAdoptForm petAdoptForm, UserDto userDto) {
		String petName = petAdoptForm.getPetName();
		String breed = petAdoptForm.getBreed();
		int petGender = petAdoptForm.getPetGender();
		LocalDate petBirth = petAdoptForm.getPetBirth();

		UserCanNotFindException userCanNotFindException
				= new UserCanNotFindException(getMessage("validation.constraints.canNotFindUser.message"));

		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> userCanNotFindException);

		PetBreedCanNotFindException petBreedCanNotFindException
				= new PetBreedCanNotFindException(getMessage("validation.constraints.canNotFindBreed.message"));

		PetBreed petBreed = petBreedRepository.findByBreed(breed)
				.orElseThrow(() -> petBreedCanNotFindException);
    
		Pet pet = Pet.builder()
				.petName(petName)
				.petBreed(petBreed)
				.petGender(petGender)
				.petBirth(petBirth)
				.user(user)
				.build();

		petRepository.save(pet);
	}

	public List<PetsForm> showPets(UserDto userDto) {
		return petRepository.findByUserId(userDto.getId())
				.stream()
				.map(pet -> new PetsForm(pet.getId(), pet.getPetName()))
				.toList();
	}

	@Transactional
	public void leave(PetLeaveForm petLeaveForm) {
		PetCanNotFindException petCanNotFindException
				= new PetCanNotFindException(getMessage("validation.constraints.canNotFindPet.message"));

		Pet pet = petRepository.findById(petLeaveForm.getPetId())
				.orElseThrow(() -> petCanNotFindException);
		pet.leave(petLeaveForm.getPetLeaveDate());
	}

	private String getMessage(String message) {
		return messageSource.getMessage(message, null, KOREAN);
	}
}
