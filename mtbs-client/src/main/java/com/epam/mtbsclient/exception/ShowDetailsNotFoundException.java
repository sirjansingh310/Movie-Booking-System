package com.epam.mtbsclient.exception;

public class ShowDetailsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ShowDetailsNotFoundException() {
		super("No show details found");
	}
}
