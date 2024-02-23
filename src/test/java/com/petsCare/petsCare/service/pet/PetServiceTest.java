package com.petsCare.petsCare.service.pet;

import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.pet.PetType;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.repository.pet.PetBreedRepository;
import com.petsCare.petsCare.repository.pet.PetRepository;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

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
		String loginId = "이복덩";
		PetAdoptForm petAdoptForm = new PetAdoptForm(petName, thumbnail, breed, petGender, petBirth, loginId);
		when(petBreedRepository.findByBreed(any(String.class)))
				.thenReturn(of(new PetBreed("닥스훈트", new PetType("강아지"))));

		when(userRepository.findByLoginId(any(String.class)))
				.thenReturn(of(new User("provider", loginId, "닉네임", "profileImage", "USER")));

		//when //then
		assertThatCode(() -> petService.adopt(petAdoptForm));

	}
}