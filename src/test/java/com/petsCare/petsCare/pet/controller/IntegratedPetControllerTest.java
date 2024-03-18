package com.petsCare.petsCare.pet.controller;

import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.dto.form.UserJoinForm;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.nimbusds.common.contenttype.ContentType.IMAGE_JPEG;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
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
		UserJoinForm userJoinForm = new UserJoinForm("testUser", "testUser!23", "진세진");

		mockMvc.perform(
				multipart("/join")
						.param("loginId", userJoinForm.getLoginId())
						.param("password", userJoinForm.getPassword())
						.param("username", userJoinForm.getUsername())
						.contentType(MediaType.MULTIPART_FORM_DATA)
		);

		User user = userRepository.findByLoginId(userJoinForm.getLoginId()).get();
		UserDto userDto = new UserDto(user);
		String url = "/pets/adopt";
		String petName = "이복댕";
		MockMultipartFile thumbnail = new MockMultipartFile("썸네일.png", "이복댕.png", IMAGE_JPEG.getType(), "이복댕.png".getBytes(StandardCharsets.UTF_8));

		String breed = "닥스훈트";
		int petGender = 1;
		LocalDate petBirth = LocalDate.now();

		//when
		ResultActions resultActions = mockMvc.perform(multipart(url)
						.file(thumbnail)
				.with(oauth2Login().oauth2User(new CustomOAuth2User(userDto)))
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
		UserJoinForm userJoinForm = new UserJoinForm("testUser", "testUser!23", "진세진");

		mockMvc.perform(
				multipart("/join")
						.param("loginId", userJoinForm.getLoginId())
						.param("password", userJoinForm.getPassword())
						.param("username", userJoinForm.getUsername())
						.contentType(MediaType.MULTIPART_FORM_DATA)
		);

		String url = "/pets";
		User user = userRepository.findByLoginId(userJoinForm.getLoginId()).get();
		UserDto userDto = new UserDto(user);

		//when
		ResultActions resultActions = mockMvc.perform(get(url)
						.with(oauth2Login().oauth2User(new CustomOAuth2User(userDto)))
		);

		//then
		resultActions.andExpectAll(
				status().isOk()
		);
	}
}