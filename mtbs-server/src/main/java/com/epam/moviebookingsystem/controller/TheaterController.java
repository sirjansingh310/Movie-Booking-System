package com.epam.moviebookingsystem.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.service.TheaterService;

@RestController
public class TheaterController {
	@Autowired
	private TheaterService theaterService;
	private Logger logger = LogManager.getLogger(TheaterController.class);

	@GetMapping("theater/{userLocation}/{userMovie}")
	public ResponseEntity<List<Theater>> getTheaters(@PathVariable String userLocation,
			@PathVariable String userMovie) {
		ResponseEntity<List<Theater>> responseEntity;
		List<Theater> theatersList;
		try {
			theatersList = theaterService.getTheatersList(userLocation, userMovie);
			if (theatersList.isEmpty())
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				responseEntity = new ResponseEntity<>(theatersList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/theater")
	public ResponseEntity<List<Theater>> getAllTheaters() {
		ResponseEntity<List<Theater>> responseEntity;
		List<Theater> theatersList;
		try {
			theatersList = theaterService.getAllTheaters();
			if (theatersList.isEmpty())
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				responseEntity = new ResponseEntity<>(theatersList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@PostMapping("theater/{name}/{locationId}")
	public ResponseEntity<String> addTheater(@PathVariable String name, @PathVariable Integer locationId){
		ResponseEntity<String> responseEntity;
		try {
			theaterService.addTheater(name, locationId);
			responseEntity = new ResponseEntity<>("Theater added", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@DeleteMapping("theater/{theaterId}")
	public ResponseEntity<String> deleteTheater(@PathVariable Integer theaterId){
		ResponseEntity<String> responseEntity;
		try {
			theaterService.deleteTheater(theaterId);
			responseEntity = new ResponseEntity<>("Theater deleted", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
}
