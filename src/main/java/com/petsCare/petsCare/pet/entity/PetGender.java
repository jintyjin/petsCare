package com.petsCare.petsCare.pet.entity;

public enum PetGender {
	M(0), F(1);

	private final int gender;

	PetGender(int gender) {
		this.gender = gender;
	}

	public int getGender() {
		return gender;
	}
}
