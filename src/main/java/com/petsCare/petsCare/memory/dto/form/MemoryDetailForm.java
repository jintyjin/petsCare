package com.petsCare.petsCare.memory.dto.form;

import com.petsCare.petsCare.memory.entity.Gps;
import com.petsCare.petsCare.memory.entity.ImageSize;
import com.petsCare.petsCare.memory.entity.Memory;
import com.petsCare.petsCare.memory.entity.UploadFile;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MemoryDetailForm {

	private Long id;

	private String info;

	private LocalDateTime imageTime;

	private String uploadFileName;

	private String saveFileName;

	private Integer width;

	private Integer height;

	private BigDecimal latitude;

	private BigDecimal longitude;

	@QueryProjection
	public MemoryDetailForm(Memory memory) {
		this.id = memory.getId();
		this.info = memory.getInfo();
		this.imageTime = memory.getManageTime().getImageTime();
		this.uploadFileName = memory.getUploadFile().getUploadFileName();
		this.saveFileName = memory.getUploadFile().getSaveFileName();
		this.width = memory.getImageSize().getWidth();
		this.height = memory.getImageSize().getHeight();
		this.latitude = memory.getGps().getLatitude();
		this.longitude = memory.getGps().getLongitude();
	}
}
