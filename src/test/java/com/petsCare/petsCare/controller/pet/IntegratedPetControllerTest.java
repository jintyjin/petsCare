package com.petsCare.petsCare.controller.pet;

import com.petsCare.petsCare.dto.oauth2.CustomOAuth2User;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.nimbusds.common.contenttype.ContentType.IMAGE_JPEG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class IntegratedPetControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("반려 동물 등록 성공")
	void adoptSuccess() throws Exception {
		//given
		User user = userRepository.findById(1L).get();
		String url = "/pets/adopt";
		String petName = "이복댕";
		MockMultipartFile thumbnail = new MockMultipartFile("썸네일.png", "이복댕.png", IMAGE_JPEG.getType(), "이복댕.png".getBytes(StandardCharsets.UTF_8));

		String breed = "닥스훈트";
		int petGender = 1;
		LocalDate petBirth = LocalDate.now();

		//when
		ResultActions resultActions = mockMvc.perform(multipart(url)
						.file(thumbnail)
						.with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(new CustomOAuth2User(user)))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("petName", petName)
				.param("breed", breed)
				.param("petGender", String.valueOf(petGender))
				.param("petBirth", String.valueOf(petBirth))
		);

		//then
		resultActions.andExpectAll(
				redirectedUrl("/")
		);
	}

	@Test
	@DisplayName("반려 동물 불러오기 성공")
	void petsSuccess() throws Exception {
		//given
		String url = "/pets";
		User user = userRepository.findById(1L).get();

		//when
		ResultActions resultActions = mockMvc.perform(get(url)
				.with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(new CustomOAuth2User(user)))
		);

		//then
		resultActions.andExpectAll(
				status().isOk()
		);
	}
}