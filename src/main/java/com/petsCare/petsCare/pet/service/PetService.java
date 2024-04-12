package com.petsCare.petsCare.pet.service;
import com.petsCare.petsCare.memory.service.MemoryService;
import com.petsCare.petsCare.pet.dto.form.*;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.exception.PetException;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.user.exception.UserException;
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

	private final JpaPetRepository jpaPetRepository;
	private final PetBreedRepository petBreedRepository;
	private final UserRepository userRepository;
	private final MemoryService memoryService;

	@Transactional
	public void adopt(PetAdoptForm petAdoptForm, UserDto userDto) {
		String petName = petAdoptForm.getPetName();
		Long breedId = petAdoptForm.getBreedId();
		int petGender = petAdoptForm.getPetGender();
		LocalDate petBirth = petAdoptForm.getPetBirth();

		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> UserException.USER_CAN_NOT_FIND_EXCEPTION);

		PetBreed petBreed = petBreedRepository.findById(breedId)
				.orElseThrow(() -> PetException.PET_BREED_CAN_NOT_FIND_EXCEPTION);

		if (petBirth.isAfter(LocalDate.now())) {
			throw PetException.PET_BIRTH_CAN_NOT_AFTER_TODAY_EXCEPTION;
		}

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
		if (petDetailForm.getLeaveTime().isBefore(petDetailForm.getBornTime())) {
			throw PetException.PET_LEAVE_CAN_NOT_BEFORE_BIRTH_EXCEPTION;
		}

		if (petDetailForm.getLeaveTime().isAfter(LocalDate.now())) {
			throw PetException.PET_LEAVE_CAN_NOT_AFTER_TODAY_EXCEPTION;
		}

		Pet pet = findPet(petDetailForm.getId());

		pet.leave(petDetailForm.getLeaveTime());
	}

	public Pet findPet(Long petId) {
		return jpaPetRepository.findById(petId)
				.orElseThrow(() -> PetException.PET_CAN_NOT_FIND_EXCEPTION);
	}
}
