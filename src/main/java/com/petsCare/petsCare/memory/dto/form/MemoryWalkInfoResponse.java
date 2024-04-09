package com.petsCare.petsCare.memory.dto.form;

import com.petsCare.petsCare.memory.entity.Gps;
import com.petsCare.petsCare.memory.entity.ImageSize;
import com.petsCare.petsCare.memory.entity.Memory;
import com.petsCare.petsCare.memory.entity.UploadFile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoryWalkInfoResponse {

	private Long memoryId;

	private String info;

	private LocalDateTime imageTime;

	private UploadFile uploadFile;

	private ImageSize imageSize;

	private Gps gps;

	@QueryProjection
	public MemoryWalkInfoResponse(Memory memory) {
		this.memoryId = memory.getId();
		this.info = memory.getInfo();
		this.imageTime = memory.getManageTime().getImageTime();
		this.uploadFile = memory.getUploadFile();
		this.imageSize = memory.getImageSize();
		this.gps = memory.getGps();
	}
}
