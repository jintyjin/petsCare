package com.petsCare.petsCare.memory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gps {

	@Column(precision = 20, scale = 16)
	private BigDecimal latitude;

	@Column(precision = 20, scale = 16)
	private BigDecimal longitude;

	public Gps(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
