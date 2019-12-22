package com.epam.moviebookingsystem.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.moviebookingsystem.entity.Booking;
import com.epam.moviebookingsystem.service.BookingService;

@RestController
public class BookingController {
	private Logger logger = LogManager.getLogger(BookingController.class);
	@Autowired
	private BookingService bookingService; 
	
	@GetMapping("booking/{showId}")
	public ResponseEntity<Map<Object, Object>> getSeatLayout(@PathVariable Integer showId) {
		ResponseEntity<Map<Object, Object>> responseEntity;
		Map<Object, Object> map;
		try {
			map = bookingService.getSeatLayout(showId);
			if (map.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@PostMapping("booking/{showId}/{username}")
	public ResponseEntity<Booking> bookSeats(@PathVariable Integer showId, @PathVariable String username, @RequestBody String[] seats) {
		ResponseEntity<Booking> responseEntity;
		Booking booking;
		try {
			Optional<Booking> optionalBooking = bookingService.bookTickets(showId, username, seats);
			if (optionalBooking.isPresent()) {
				booking = optionalBooking.get();
				responseEntity = new ResponseEntity<>(booking, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
}
