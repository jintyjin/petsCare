package com.petsCare.petsCare.pet;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.pet.exception.PetBreedCanNotFindException;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.dto.form.PetAdoptForm;
import com.petsCare.petsCare.pet.dto.form.PetLeaveForm;
import com.petsCare.petsCare.pet.dto.form.PetsForm;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.PetRepository;
import com.petsCare.petsCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final PetBreedRepository petBreedRepository;
	private final UserRepository userRepository;

	@Transactional
	public void adopt(PetAdoptForm petAdoptForm, User user) {
		String petName = petAdoptForm.getPetName();
		String breed = petAdoptForm.getBreed();
		int petGender = petAdoptForm.getPetGender();
		LocalDate petBirth = petAdoptForm.getPetBirth();
    
		String breedErrorCode = "canNotFindBreed.petAdoptForm.breed";
		PetBreedCanNotFindException petBreedCanNotFindException = new PetBreedCanNotFindException(breedErrorCode);

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

	public List<PetsForm> showPets(User user) {
		return petRepository.findByUserId(user.getId())
				.stream()
				.map(pet -> new PetsForm(pet.getId(), pet.getPetName()))
				.toList();
	}

	@Transactional
	public void leave(PetLeaveForm petLeaveForm) {
		PetCanNotFindException petCanNotFindException
				= new PetCanNotFindException("canNotFindPet.petLeaveForm.petLeaveDate");
		Pet pet = petRepository.findById(petLeaveForm.getPetId())
				.orElseThrow(() -> petCanNotFindException);
		pet.leave(petLeaveForm.getPetLeaveDate());
	}
}