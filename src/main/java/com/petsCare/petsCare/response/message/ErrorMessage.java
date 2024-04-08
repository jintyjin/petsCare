package com.petsCare.petsCare.response.message;

public enum ErrorMessage {
	USER_DUPLICATED_LOGIN_ID_EXCEPTION("validation.constraints.duplicatedLoginIdUser.message"),
	USER_CAN_NOT_FIND_EXCEPTION("validation.constraints.canNotFindUser.message"),
	PET_BREED_CAN_NOT_FIND_EXCEPTION("validation.constraints.canNotFindBreed.message"),
	PET_CAN_NOT_FIND_EXCEPTION("validation.constraints.canNotFindPet.message"),
	MEMORY_EXCEPTION("validation.constraints.memory.message"),
	MEMORY_DUPLICATED_MEMORY_PATH_EXCEPTION("validation.constraints.duplicatedPathMemory.message");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
