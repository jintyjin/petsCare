package com.petsCare.petsCare.pet.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public interface PetException {
	public static final PetBreedCanNotFindException PET_BREED_CAN_NOT_FIND_EXCEPTION = new PetBreedCanNotFindException(ErrorMessage.PET_BREED_CAN_NOT_FIND_EXCEPTION);
	public static final PetCanNotFindException PET_CAN_NOT_FIND_EXCEPTION = new PetCanNotFindException(ErrorMessage.PET_CAN_NOT_FIND_EXCEPTION);
}
