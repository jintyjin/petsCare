package com.petsCare.petsCare.memory.dto.form;

import com.petsCare.petsCare.memory.entity.MemoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MemoryWalkResponseForm {

	private Long memoryId;

	private String saveFileName;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private LocalDateTime imageTime;

	private Integer width;

	private Integer height;

	private MemoryType memoryType;

	@QueryProjection
	public MemoryWalkResponseForm(Long memoryId, String saveFileName, BigDecimal latitude, BigDecimal longitude, LocalDateTime imageTime, Integer width, Integer height, MemoryType memoryType) {
		this.memoryId = memoryId;
		this.saveFileName = saveFileName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imageTime = imageTime;
		this.width = width;
		this.height = height;
		this.memoryType = memoryType;
	}
}
