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

	public static PetGender getLabel(int gender) {
		for (PetGender petGender : PetGender.values()) {
			if (petGender.getGender() == gender) {
				return petGender;
			}
		}

		throw new IllegalArgumentException("올바르지 않은 성별 값입니다.");
	}
}
