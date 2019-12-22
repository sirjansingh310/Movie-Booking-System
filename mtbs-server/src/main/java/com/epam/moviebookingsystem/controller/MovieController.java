package com.epam.moviebookingsystem.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.service.MovieService;

@RestController
public class MovieController {
	@Autowired
	private MovieService movieService;
	private Logger logger = LogManager.getLogger(MovieController.class);

	@GetMapping("movie/{userLocation}")
	public ResponseEntity<List<Movie>> getMoviesIsUserLocation(@PathVariable String userLocation) {
		ResponseEntity<List<Movie>> responseEntity;
		List<Movie> moviesInUserLocation;
		try {
			moviesInUserLocation = movieService.getMoviesByLocation(userLocation);
			if (moviesInUserLocation.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				responseEntity = new ResponseEntity<>(moviesInUserLocation, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("movie")
	public ResponseEntity<List<Map<String, Object>>> getAllMovies() {
		ResponseEntity<List<Map<String, Object>>> responseEntity;
		List<Map<String, Object>> moviesWithLocationsList;
		try {
			moviesWithLocationsList = movieService.getAllMovies();
			if (moviesWithLocationsList.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				responseEntity = new ResponseEntity<>(moviesWithLocationsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@PostMapping("movie/{name}")
	public ResponseEntity<String> addMovie(@PathVariable String name, @RequestBody Integer[] locationIds) {
		ResponseEntity<String> responseEntity;
		try {
			movieService.addMovie(name, locationIds);
			responseEntity = new ResponseEntity<>("Movie added", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Movie already exists", HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@DeleteMapping("movie/{movieId}")
	public ResponseEntity<String> deleteMovie(@PathVariable Integer movieId){
		ResponseEntity<String> responseEntity;
		try {
			movieService.deleteMovie(movieId);
			responseEntity = new ResponseEntity<>("Movie deleted", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Error while deleting movie", HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
	@PostMapping("/movie/{movieId}/{name}")
	public ResponseEntity<String> editMovie(@PathVariable Integer movieId, @PathVariable String name, @RequestBody Integer[] locationIds){
		ResponseEntity<String> responseEntity;
		try {
			movieService.editMovie(movieId, name, locationIds);
			responseEntity = new ResponseEntity<>("Movie edited", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Movie already exists", HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	} 
 }
