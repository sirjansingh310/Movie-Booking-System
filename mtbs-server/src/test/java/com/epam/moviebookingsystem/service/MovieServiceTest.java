package com.epam.moviebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.MovieInLocation;
import com.epam.moviebookingsystem.repository.LocationRepository;
import com.epam.moviebookingsystem.repository.MovieInLocationRepository;

class MovieServiceTest {

	@Mock
	private MovieInLocationRepository movieInLocationRepositoryMock;
	@Mock
	private LocationRepository locationRepositoryMock;
	@InjectMocks
	private MovieService movieService;
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void getMoviesByLocationTest() {
		Location location = new Location();
		Movie movie = new Movie();
		when(locationRepositoryMock.findByCityName("mock city name")).thenReturn(location);
		List<MovieInLocation> movieInLocationList = new ArrayList<>();
		MovieInLocation movieInLocation = new MovieInLocation();
		movieInLocation.setMovie(movie);
		movieInLocation.setLocation(location);
		movieInLocationList.add(movieInLocation);
		when(movieInLocationRepositoryMock.findByLocation(location)).thenReturn(movieInLocationList);
		List<Movie> moviesList = new ArrayList<>();
		moviesList.add(movie);
		assertEquals(moviesList, movieService.getMoviesByLocation("mock city name"));	
	}
	
}
