package com.epam.mtbsclient.exception;

public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InternalServerErrorException() {
		super("There seems to be a problem at the server. Please try again");
	}
}
