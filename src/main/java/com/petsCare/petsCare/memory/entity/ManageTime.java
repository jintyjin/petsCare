package com.petsCare.petsCare.memory.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManageTime {

	private LocalDateTime imageTime;

	private LocalDateTime deleteTime;

	public ManageTime(LocalDateTime imageTime) {
		this.imageTime = imageTime;
	}

	public void delete(LocalDateTime deleteTime) {
		this.deleteTime = deleteTime;
	}
}
