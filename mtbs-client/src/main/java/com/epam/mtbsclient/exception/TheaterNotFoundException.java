package com.epam.mtbsclient.exception;

public class TheaterNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public TheaterNotFoundException(String movie) {
		super("No theaters found for movie" + movie);
	}
}
