package com.petsCare.petsCare.user.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public interface UserException {

	public static final UserCanNotFindException USER_CAN_NOT_FIND_EXCEPTION = new UserCanNotFindException(ErrorMessage.USER_CAN_NOT_FIND_EXCEPTION);
	public static final UserDuplicatedException USER_DUPLICATED_EXCEPTION = new UserDuplicatedException(ErrorMessage.USER_DUPLICATED_LOGIN_ID_EXCEPTION);
}
