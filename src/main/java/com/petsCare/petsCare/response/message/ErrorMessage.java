package com.petsCare.petsCare.response.message;

public interface ErrorMessage {
	public static final String EXCEPTION = "validation.constraints.exception.message";
	public static final String USER_DUPLICATED_LOGIN_ID_EXCEPTION = "validation.constraints.duplicatedLoginIdUser.message";
	public static final String USER_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindUser.message";
	public static final String PET_BREED_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindBreed.message";
	public static final String PET_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindPet.message";
	public static final String PET_BIRTH_CAN_NOT_AFTER_TODAY_EXCEPTION = "validation.constraints.canNotAfterTodayPetBirth.message";
	public static final String PET_LEAVE_CAN_NOT_BEFORE_BIRTH_EXCEPTION = "validation.constraints.canNotBeforeBirthPetLeave.message";
	public static final String PET_LEAVE_CAN_NOT_AFTER_TODAY_EXCEPTION = "validation.constraints.canNotAfterTodayPetLeave.message";
	public static final String MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION = "validation.constraints.duplicatedPathMemory.message";
}
