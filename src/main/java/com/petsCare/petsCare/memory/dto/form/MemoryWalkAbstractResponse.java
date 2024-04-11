package com.petsCare.petsCare.memory.dto.form;

import com.petsCare.petsCare.memory.entity.Gps;
import com.petsCare.petsCare.memory.entity.ImageSize;
import com.petsCare.petsCare.memory.entity.Memory;
import com.petsCare.petsCare.memory.entity.MemoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoryWalkAbstractResponse {

	private Long memoryId;

	private String saveFileName;

	private Gps gps;

	private LocalDateTime imageTime;

	private ImageSize imageSize;

	private MemoryType memoryType;

	@QueryProjection
	public MemoryWalkAbstractResponse(Memory memory) {
		this.memoryId = memory.getId();
		this.saveFileName = memory.getUploadFile().getSaveFileName();
		this.gps = memory.getGps();
		this.imageTime = memory.getManageTime().getImageTime();
		this.imageSize = memory.getImageSize();
		this.memoryType = memory.getMemoryType();
	}
}
