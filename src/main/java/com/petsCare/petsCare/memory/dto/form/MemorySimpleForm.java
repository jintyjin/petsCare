package com.petsCare.petsCare.memory.dto.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
