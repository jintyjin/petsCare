package com.petsCare.petsCare.pet.dto.form;

import com.petsCare.petsCare.memory.entity.MemoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PetsForm {

	private Long id;

	private String name;

	private String path;

	private MemoryType type;

	@QueryProjection
	public PetsForm(Long id, String name, String path, MemoryType type) {
		this.id = id;
		this.name = name;
		this.path = path;
		this.type = type;
	}
}
