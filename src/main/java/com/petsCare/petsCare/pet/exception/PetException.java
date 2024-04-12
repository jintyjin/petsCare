package com.petsCare.petsCare.pet.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public interface PetException {
	public static final PetBreedCanNotFindException PET_BREED_CAN_NOT_FIND_EXCEPTION = new PetBreedCanNotFindException(ErrorMessage.PET_BREED_CAN_NOT_FIND_EXCEPTION);
	public static final PetCanNotFindException PET_CAN_NOT_FIND_EXCEPTION = new PetCanNotFindException(ErrorMessage.PET_CAN_NOT_FIND_EXCEPTION);
	public static final PetBirthCanNotAfterTodayException PET_BIRTH_CAN_NOT_AFTER_TODAY_EXCEPTION = new PetBirthCanNotAfterTodayException(ErrorMessage.PET_BIRTH_CAN_NOT_AFTER_TODAY_EXCEPTION);
	public static final PetLeaveCanNotBeforeBirthException PET_LEAVE_CAN_NOT_BEFORE_BIRTH_EXCEPTION = new PetLeaveCanNotBeforeBirthException(ErrorMessage.PET_LEAVE_CAN_NOT_BEFORE_BIRTH_EXCEPTION);
	public static final PetLeaveCanNotAfterTodayException PET_LEAVE_CAN_NOT_AFTER_TODAY_EXCEPTION = new PetLeaveCanNotAfterTodayException(ErrorMessage.PET_LEAVE_CAN_NOT_AFTER_TODAY_EXCEPTION);
}
