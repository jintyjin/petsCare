package com.petsCare.petsCare.memory.dto.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemorySimpleForm {

	private Long memoryId;

	private String type;

	private String path;

	@QueryProjection
	public MemorySimpleForm(Long memoryId, String type, String path) {
		this.memoryId = memoryId;
		this.type = type;
		this.path = path;
	}
}
