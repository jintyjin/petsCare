package com.petsCare.petsCare.memory.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageSize {

	private Integer width;
	private Integer height;

	public ImageSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}
}
