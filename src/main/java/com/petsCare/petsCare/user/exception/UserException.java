package com.petsCare.petsCare.user.exception;

import com.petsCare.petsCare.response.message.ErrorMessage;

public class UserException extends RuntimeException {
	private final ErrorMessage errorMessage;

	public static final UserException DUPLICATED_LOGIN_ID_EXCEPTION = new UserException(ErrorMessage.USER_DUPLICATED_LOGIN_ID_EXCEPTION);
	public static final UserException USER_CAN_NOT_FIND_EXCEPTION = new UserException(ErrorMessage.USER_CAN_NOT_FIND_EXCEPTION);

	public UserException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
