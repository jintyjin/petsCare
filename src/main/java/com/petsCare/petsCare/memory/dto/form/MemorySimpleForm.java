package com.petsCare.petsCare.memory.dto.form;

import com.petsCare.petsCare.memory.entity.MemoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemorySimpleForm {

	private Long memoryId;

	private MemoryType type;

	private String path;

	@QueryProjection
	public MemorySimpleForm(Long memoryId, MemoryType type, String path) {
		this.memoryId = memoryId;
		this.type = type;
		this.path = path;
	}
}
