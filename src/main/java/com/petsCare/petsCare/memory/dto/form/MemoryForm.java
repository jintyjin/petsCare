package com.petsCare.petsCare.memory.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoryForm {

	@NotNull
	private Long petId;

	@NotEmpty
	private List<MultipartFile> files;
}
