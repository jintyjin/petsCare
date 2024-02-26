package com.petsCare.petsCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.service.pet.PetService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.nimbusds.common.contenttype.ContentType.IMAGE_JPEG;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {PetController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
class PetControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PetService petService;

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Before("초기화")
	public void before() {
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	
	@Test
	@DisplayName("펫 등록 성공")
	void adoptSuccess() throws Exception {
		//given
		String url = "/pets/adopt";

		String petName = "이복댕";
		MockMultipartFile thumbnail = new MockMultipartFile("썸네일.png", "이복댕.png", IMAGE_JPEG.getType(), "이복댕.png".getBytes(StandardCharsets.UTF_8));

		String breed = "닥스훈트";
		int petGender = 1;
		LocalDate petBirth = LocalDate.now();
		String loginId = "naver_testMember123";

		doNothing().when(petService).adopt(any(PetAdoptForm.class));

		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart(url)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("petName", petName)
				.param("breed", breed)
				.param("petGender", String.valueOf(petGender))
				.param("petBirth", String.valueOf(petBirth))
				.param("loginId", loginId)
		);

		//then
		resultActions.andExpectAll(
				status().is3xxRedirection()
		);
	}
}