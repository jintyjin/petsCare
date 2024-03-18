package com.petsCare.petsCare.memory.service;

import com.petsCare.petsCare.memory.dto.form.MemoryMakeForm;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.pet.repository.PetRepository;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemoryServiceTest {

	@InjectMocks
	MemoryService memoryService;

	@Mock
	PetRepository petRepository;

	@Mock
	MessageSource messageSource;

	@Test
	@DisplayName(value = "추억 등록 성공")
	void makeSuccess() throws Exception {
		//given
		MultipartFile file1 = new MockMultipartFile(UUID.randomUUID().toString(), "1234.jpg", "image/jpg", new FileInputStream("/Users/jinsejin/Desktop/files/images/" + "/KakaoTalk_20200629_004002411.jpg"));
		MultipartFile file2 = new MockMultipartFile(UUID.randomUUID().toString(), "1234.jpg", "image/jpg", new FileInputStream("/Users/jinsejin/Desktop/files/images/" + "/KakaoTalk_20200629_004002411.jpg"));
		MultipartFile file3 = new MockMultipartFile(UUID.randomUUID().toString(), "1234.jpg", "image/jpg", new FileInputStream("/Users/jinsejin/Desktop/files/images/" + "/KakaoTalk_20200629_004002411.jpg"));

		List<MultipartFile> list = new ArrayList<>();
		list.add(file1);
		list.add(file2);
		list.add(file3);

		User user = User.builder()
				.provider("naver")
				.loginId("naver_jinjin")
				.username("JJ")
				.profileImage("jj.png")
				.role("ROLE_USER")
				.build();

		PetType petType = new PetType("강아지");
		PetBreed petBreed = new PetBreed("닥스훈트", petType);
		Pet pet = new Pet("이복댕", "null", petBreed, 1, LocalDate.now(), user);
		given(petRepository.findById(anyLong()))
				.willReturn(ofNullable(pet));

		MemoryMakeForm memoryMakeForm = new MemoryMakeForm(1L, list);

		//when // then
		assertThatCode(() -> memoryService.make(memoryMakeForm, new UserDto(user)));
	}
}