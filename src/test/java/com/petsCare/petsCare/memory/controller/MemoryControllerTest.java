package com.petsCare.petsCare.memory.controller;

import com.petsCare.petsCare.memory.dto.form.MemoryForm;
import com.petsCare.petsCare.memory.service.MemoryService;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.User;
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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {MemoryController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
class MemoryControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	MemoryService memoryService;

	@Test
	@DisplayName("추억 만들기 성공")
	void makeSuccess() throws Exception {
		//given
		MockMultipartFile file = new MockMultipartFile(UUID.randomUUID().toString(), "1234.jpg", "image/jpg", new FileInputStream("/Users/jinsejin/Desktop/files/images/" + "/KakaoTalk_20200629_004002411.jpg"));
		MemoryForm memoryMakeForm = new MemoryForm();

		given(memoryService.make(any(MemoryForm.class), any(UserDto.class)))
				.willReturn(new ArrayList<>());

		String url = "/memory/make";

		User user = new User(1L, "local", "testuser", "Test User", "test.jpg", "ROLE_USER", new ArrayList<>());
		UserDto userDto = new UserDto(user);

		//when
		ResultActions resultActions = mockMvc.perform(multipart(url)
						.file("files", file.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("userDto", userDto)
				.param("petId", memoryMakeForm.getPetId().toString())
		);

		//then
		resultActions.andExpectAll(
				status().is3xxRedirection()
		);
	}
}