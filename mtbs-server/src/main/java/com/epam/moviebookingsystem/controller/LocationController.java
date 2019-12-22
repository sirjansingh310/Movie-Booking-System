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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.service.LocationService;

@RestController
public class LocationController {

	@Autowired
	private LocationService locationService;
	private Logger logger = LogManager.getLogger(LocationController.class);

	@GetMapping("location")
	public ResponseEntity<List<Location>> getAllLocations() {
		ResponseEntity<List<Location>> responseEntity;
		List<Location> allLocations;
		try {
			allLocations = locationService.getAllLocations();
			if (allLocations.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				responseEntity = new ResponseEntity<>(allLocations, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@PostMapping("location/{cityName}")
	public ResponseEntity<String> addLocation(@PathVariable String cityName){
		ResponseEntity<String> responseEntity;
		try {
			locationService.addLocation(cityName);
			responseEntity = new ResponseEntity<>("Location added", HttpStatus.OK);
		}
		catch(Exception e) {
			responseEntity = new ResponseEntity<>("Location already exists",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@PutMapping("location/{locationId}/{newCityName}")
	public ResponseEntity<String> updateLocation(@PathVariable Integer locationId, @PathVariable String newCityName){
		ResponseEntity<String> responseEntity;
		try {
			locationService.updateLocationById(locationId, newCityName);
			responseEntity = new ResponseEntity<>("Location updated", HttpStatus.OK);
		}
		catch(Exception e) {
			responseEntity = new ResponseEntity<>("Location already exists ".concat(newCityName),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	@DeleteMapping("location/{locationId}")
	public ResponseEntity<String> deleteLocation(@PathVariable Integer locationId){
		ResponseEntity<String> responseEntity;
		try {
			locationService.deleteLocation(locationId);
			responseEntity = new ResponseEntity<>("Location deleted", HttpStatus.OK);
		}
		catch(Exception e) {
			responseEntity = new ResponseEntity<>("Couldn't delete the location",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
