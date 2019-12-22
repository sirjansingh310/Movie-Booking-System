package com.epam.mtbsclient.exception;

public class MovieNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public MovieNotFoundException(String location) {
		super("No movies found at ".concat(location));
	}
}
