package com.petsCare.petsCare.user.dto;

import com.petsCare.petsCare.user.entity.User;
import lombok.Data;

@Data
public class UserDto {

	private Long id;

	private String provider;

	private String loginId;

	private String username;

	private String profileImage;

	private String role;

	public UserDto(User user) {
		this.id = user.getId();
		this.provider = user.getProvider();
		this.loginId = user.getLoginId();
		this.username = user.getUsername();
		this.profileImage = user.getProfileImage();
		this.role = user.getRole();
	}
}
