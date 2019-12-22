package com.epam.mtbsclient.exception;

public class BookingNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	public BookingNotFoundException(Integer showId) {
		super("Bookings not found for show id".concat(Integer.toString(showId)));
	}
}
