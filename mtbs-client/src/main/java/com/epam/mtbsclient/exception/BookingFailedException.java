package com.epam.mtbsclient.exception;

import java.util.Arrays;

public class BookingFailedException extends Exception{
	private static final long serialVersionUID = 1L;
	public BookingFailedException(String seats[], Integer showId) {
		super("Booking failed for showid".concat(Integer.toString(showId)).concat(" for seats ").concat(Arrays.toString(seats)));
	}
}
