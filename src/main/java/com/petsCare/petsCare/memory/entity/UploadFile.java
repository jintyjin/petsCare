package com.petsCare.petsCare.memory.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

	private String uploadFileName;
	private String saveFileName;

	public UploadFile(String uploadFileName, String saveFileName) {
		this.uploadFileName = uploadFileName;
		this.saveFileName = saveFileName;
	}
}
