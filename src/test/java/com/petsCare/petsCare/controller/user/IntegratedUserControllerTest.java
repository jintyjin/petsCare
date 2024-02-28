package com.petsCare.petsCare.controller.user;

import com.petsCare.petsCare.form.user.UserJoinForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegratedUserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("회원 가입 성공")
	@Transactional
	void joinSuccess() throws Exception {
		//given
		UserJoinForm userJoinForm1 = new UserJoinForm("", "", "");
		UserJoinForm userJoinForm2 = new UserJoinForm("testMember123", "testUser!23", "에세이르");
		UserJoinForm userJoinForm3 = new UserJoinForm("testUser", "testUser!23", "에세이르1");

		//when
		ResultActions perform1 = mockMvc.perform(
				multipart("/user/join")
						.param("loginId", userJoinForm1.getLoginId())
						.param("password", userJoinForm1.getPassword())
						.param("nickName", userJoinForm1.getNickName())
						.contentType(MediaType.MULTIPART_FORM_DATA)
		);
		ResultActions perform2 = mockMvc.perform(
				multipart("/user/join")
						.param("loginId", userJoinForm2.getLoginId())
						.param("password", userJoinForm2.getPassword())
						.param("nickName", userJoinForm2.getNickName())
						.contentType(MediaType.MULTIPART_FORM_DATA)
		);
		ResultActions perform3 = mockMvc.perform(
				multipart("/user/join")
						.param("loginId", userJoinForm3.getLoginId())
						.param("password", userJoinForm3.getPassword())
						.param("nickName", userJoinForm3.getNickName())
						.contentType(MediaType.MULTIPART_FORM_DATA)
		);

		//then
		perform1.andExpect(status().isOk());
		perform2.andExpect(status().isOk());
		// 회원 가입까지 완료되지만 테스트 코드라 인증 정보가 없어 302오류가 뜸
		perform3.andExpect(redirectedUrl("/login"));
	}
}
