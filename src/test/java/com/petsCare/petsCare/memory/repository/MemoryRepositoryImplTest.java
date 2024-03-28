package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.MemorySimpleForm;
import com.petsCare.petsCare.memory.entity.*;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional(readOnly = true)
class MemoryRepositoryImplTest {

	@Autowired
	JpaPetRepository jpaPetRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PetBreedRepository petBreedRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	JpaMemoryRepository memoryRepository;

	@Test
	@DisplayName("추억 가져오기 성공")
	@Transactional
	void findMemorySuccess() {
		//given
		User user = User.builder()
				.provider("naver")
				.loginId("testMember123")
				.username("에세이르123")
				.profileImage("jj.png")
				.role("ROLE_USER")
				.build();
		userRepository.save(user);

		PetType petType = new PetType("강아지");
		petTypeRepository.save(petType);

		PetBreed petBreed = new PetBreed("닥스훈트", petType);
		petBreedRepository.save(petBreed);

		String petName = "이복댕";
		int petGender = 1;
		LocalDate petBirth = LocalDate.of(2014, 7, 31);
		Pet pet = new Pet(petName, null, petBreed, petGender, petBirth, user);
		Memory memory = new Memory(new UploadFile("image.jpeg", UUID.randomUUID() + ".jpeg"),
				new Gps(new BigDecimal("0.1"), new BigDecimal("0.1")),
				new ManageTime(LocalDateTime.now()), new ImageSize(1920, 1080), MemoryType.IMAGE, pet);

		jpaPetRepository.save(pet);
		memoryRepository.save(memory);

		//when
		Page<MemorySimpleForm> memorySimpleForms =
				memoryRepository.findSimpleFormByPet(pet.getId(), PageRequest.of(0, 1));

		//then
		assertThat(memorySimpleForms).extracting("memoryId").containsExactly(1L);
	}
}