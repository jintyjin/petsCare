package com.petsCare.petsCare.weight;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.weight.dto.WeightForm;
import com.petsCare.petsCare.weight.repository.JpaWeightRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class WeightServiceTest {

	@InjectMocks
	WeightService weightService;

	@Mock
	JpaWeightRepository jpaWeightRepository;

	@Mock
	JpaPetRepository petRepository;

	@Mock
	MessageSource messageSource;

	@Test
	@DisplayName("체중 측정 성공")
	void measureSuccess() {
		//given
		lenient().when(jpaWeightRepository.findPetForWeights(anyLong()))
				.thenReturn(new Pet("꼬맹이", null, new PetBreed("닥스훈트", new PetType("강아지")), 0,
						LocalDate.now(), new User("provider", "진세진", "닉네임", "profileImage", "USER")));

		//when //then
		assertThatCode(() -> weightService.measure(new WeightForm(
				new Pet("꼬맹이", null, new PetBreed("닥스훈트", new PetType("강아지")), 0,
						LocalDate.now(), new User("provider", "진세진", "닉네임", "profileImage", "USER"))
		)));
	}
}