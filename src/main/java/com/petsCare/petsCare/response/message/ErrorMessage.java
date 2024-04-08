package com.petsCare.petsCare.response.message;

public interface ErrorMessage {
	public static final String USER_DUPLICATED_LOGIN_ID_EXCEPTION = "validation.constraints.duplicatedLoginIdUser.message";
	public static final String USER_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindUser.message";
	public static final String PET_BREED_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindBreed.message";
	public static final String PET_CAN_NOT_FIND_EXCEPTION = "validation.constraints.canNotFindPet.message";
	public static final String MEMORY_EXCEPTION = "validation.constraints.memory.message";
	public static final String MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION = "validation.constraints.duplicatedPathMemory.message";
}
