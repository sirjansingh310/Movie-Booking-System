package com.epam.mtbsclient.exception;

public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	public UserAlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}

}
