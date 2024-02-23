package com.petsCare.petsCare.service.pet;

import com.petsCare.petsCare.entity.pet.Pet;
import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.exception.PetBreedCanNotFindException;
import com.petsCare.petsCare.exception.UserCanNotFindException;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.repository.pet.PetBreedRepository;
import com.petsCare.petsCare.repository.pet.PetRepository;
import com.petsCare.petsCare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.petsCare.petsCare.entity.pet.PetStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final PetBreedRepository petBreedRepository;
	private final UserRepository userRepository;

	@Transactional
	public void adopt(PetAdoptForm petAdoptForm) {
		String petName = petAdoptForm.getPetName();
		String breed = petAdoptForm.getBreed();
		int petGender = petAdoptForm.getPetGender();
		LocalDate petBirth = petAdoptForm.getPetBirth();
		String loginId = petAdoptForm.getLoginId();

		String breedErrorCode = "canNotFindBreed.petAdoptForm.breed";
		PetBreedCanNotFindException petBreedCanNotFindException = new PetBreedCanNotFindException(breedErrorCode);

		PetBreed petBreed = petBreedRepository.findByBreed(breed)
				.orElseThrow(() -> petBreedCanNotFindException);

		String userErrorCode = "canNotFindUser.petAdoptForm.loginId";
		UserCanNotFindException userCanNotFindException = new UserCanNotFindException(userErrorCode);

		User user = userRepository.findByLoginId(loginId)
				.orElseThrow(() -> userCanNotFindException);

		Pet pet = Pet.builder()
				.petName(petName)
				.petBreed(petBreed)
				.petGender(petGender)
				.petBirth(petBirth)
				.petStatus(NORMAL)
				.user(user)
				.build();

		petRepository.save(pet);
	}
}
