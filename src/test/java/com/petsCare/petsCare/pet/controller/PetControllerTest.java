package com.petsCare.petsCare.pet.controller;

import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.pet.PetController;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.pet.dto.form.PetAdoptForm;
import com.petsCare.petsCare.pet.dto.form.PetsForm;
import com.petsCare.petsCare.pet.PetService;
import com.petsCare.petsCare.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nimbusds.common.contenttype.ContentType.IMAGE_JPEG;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {PetController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
class PetControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PetService petService;

	@MockBean
	OAuth2AuthorizedClientService authorizedClientService;

	@Test
	@DisplayName("펫 등록 성공")
	void adoptSuccess() throws Exception {
		//given
		String url = "/pets/adopt";

		String petName = "이복댕";
		MockMultipartFile thumbnail = new MockMultipartFile("썸네일", "이복댕.png", IMAGE_JPEG.getType(), "이복댕.png".getBytes(StandardCharsets.UTF_8));

		String breed = "닥스훈트";
		int petGender = 1;
		LocalDate petBirth = LocalDate.now();
		String loginId = "naver_testMember123";

		User user = new User(1L, "local", "testuser", "Test User", "test.jpg", "ROLE_USER", new ArrayList<>());
		UserDto userDto = new UserDto(user);

		doNothing().when(petService).adopt(any(PetAdoptForm.class), any(UserDto.class));

		//when
		ResultActions resultActions = mockMvc.perform(multipart(url)
						.file(thumbnail)
				.with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(new CustomOAuth2User(new UserDto(user))))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("userDto", userDto)
				.param("petName", petName)
				.param("breed", breed)
				.param("petGender", String.valueOf(petGender))
				.param("petBirth", String.valueOf(petBirth))
		);

		//then
		resultActions.andExpectAll(
				status().is3xxRedirection(),
				redirectedUrl("/")
		);
	}

	@Test
	@DisplayName("반려 동물 불러오기 성공")
	void petsSuccess() throws Exception {
		//given
		String url = "/pets";

		List<PetsForm> list = new ArrayList<>();

		BDDMockito.given(petService.showPets(BDDMockito.any(UserDto.class)))
				.willReturn(list);

		User user = new User(1L, "local", "testuser", "Test User", "test.jpg", "ROLE_USER", new ArrayList<>());
		UserDto userDto = new UserDto(user);

		//when
		ResultActions resultActions = mockMvc.perform(get(url)
				.flashAttr("userDto", userDto));

		//then
		resultActions.andExpectAll(
				status().isOk()
		);
	}
}