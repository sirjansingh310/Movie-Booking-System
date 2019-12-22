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
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.repository.LocationRepository;
import com.epam.moviebookingsystem.repository.MovieRepository;
import com.epam.moviebookingsystem.repository.TheaterRepository;

class TheaterServiceTest {

	@Mock
	private TheaterRepository theaterRepositoryMock;
	@Mock
	private LocationRepository locationRepositoryMock;
	@Mock
	private MovieRepository movieRepositoryMock;
	@InjectMocks
	private TheaterService theaterService;
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void getTheatersListTest() {
		List<Theater> theatersList = new ArrayList<>();
		Location location = new Location();
		Movie movie = new Movie();
		when(locationRepositoryMock.findByCityName("mock city")).thenReturn(location);
		when(movieRepositoryMock.findByName("mock movie")).thenReturn(movie);
		when(theaterRepositoryMock.fetchTheatersByLocationAndMovie(location, movie)).thenReturn(theatersList);
		assertEquals(theatersList, theaterService.getTheatersList("mock city", "mock movie"));
	}
	
}
