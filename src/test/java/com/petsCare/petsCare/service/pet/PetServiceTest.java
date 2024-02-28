package com.petsCare.petsCare.service.pet;

import com.petsCare.petsCare.entity.pet.Pet;
import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.pet.PetType;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.form.pet.PetLeaveForm;
import com.petsCare.petsCare.form.pet.PetsForm;
import com.petsCare.petsCare.repository.pet.PetBreedRepository;
import com.petsCare.petsCare.repository.pet.PetRepository;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nimbusds.common.contenttype.ContentType.*;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

	@InjectMocks
	PetService petService;

	@Mock
	PetRepository petRepository;

	@Mock
	PetBreedRepository petBreedRepository;

	@Mock
	UserRepository userRepository;

	@Test
	@DisplayName("반려 동물 등록 성공")
	void adoptSuccess() throws Exception {
		//given
		String petName = "이복댕";
		MockMultipartFile thumbnail = new MockMultipartFile("썸네일.png", "이복댕.png", IMAGE_JPEG.getType(), "이복댕.png".getBytes(StandardCharsets.UTF_8));
		String breed = "닥스훈트";
		int petGender = 1;
		LocalDate petBirth = LocalDate.now();
		User user = new User("google", "google_loginId", "홍길동", "image.png", "ROLE_USER");
		PetAdoptForm petAdoptForm = new PetAdoptForm(petName, thumbnail, breed, petGender, petBirth);
		when(petBreedRepository.findByBreed(any(String.class)))
				.thenReturn(of(new PetBreed("닥스훈트", new PetType("강아지"))));

		//when //then
		assertThatCode(() -> petService.adopt(petAdoptForm, user));
	}

	@Test
	@DisplayName("반려 동물 상태 수정 성공")
	void leaveSuccess() {
		//given
		PetLeaveForm petLeaveForm = new PetLeaveForm(1L, LocalDate.now());
		when(petRepository.findById(1L))
				.thenReturn(of(new Pet("꼬맹이", new PetBreed("닥스훈트", new PetType("강아지")), 0,
						LocalDate.now(), new User("provider", "진세진", "닉네임", "profileImage", "USER"))));

		//when //then
		assertThatCode(() -> petService.leave(petLeaveForm));
		verify(petRepository, atLeastOnce()).findById(1L);
	}

  @Test
	@DisplayName("반려 동물들 가져오기")
	void petsSuccess() {
		//given
		List<Pet> pets = new ArrayList<>();
		User user = new User("google", "google_loginId", "홍길동", "image.png", "ROLE_USER");
		Pet pet1 = new Pet("이복댕", new PetBreed("닥스훈트", new PetType("강아지")), 1, LocalDate.now(), user);
		Pet pet2 = new Pet("이복댕", new PetBreed("믹스견", new PetType("강아지")), 0, LocalDate.now(), user);
		pets.add(pet1);
		pets.add(pet2);

		when(petRepository.findByUserId(user.getId()))
				.thenReturn(pets);

		//when
		List<PetsForm> petsForms = petService.showPets(user);

		//then
		assertThat(petsForms.size()).isEqualTo(2);
	}
}