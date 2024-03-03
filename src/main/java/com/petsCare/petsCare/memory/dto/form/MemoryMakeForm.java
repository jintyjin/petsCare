package com.petsCare.petsCare.memory.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoryMakeForm {

	@NotNull
	private Long petId;

	@NotEmpty
	private List<MultipartFile> files;

	public MemoryMakeForm(Long petId, List<MultipartFile> files) {
		this.petId = petId;
		this.files = files;
	}
}
