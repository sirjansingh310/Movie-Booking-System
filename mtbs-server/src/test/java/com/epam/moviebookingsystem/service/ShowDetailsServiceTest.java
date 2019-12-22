package com.epam.moviebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.repository.MovieRepository;
import com.epam.moviebookingsystem.repository.ShowDetailsRepository;
import com.epam.moviebookingsystem.repository.TheaterRepository;

class ShowDetailsServiceTest {

	@Mock
	private ShowDetailsRepository showDetailsRepositoryMock;
	@Mock
	private TheaterRepository theaterRepositoryMock;
	@Mock
	private MovieRepository movieRepositoryMock;
	@InjectMocks
	private ShowDetailsService showDetailsService;
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void getShowDetailsTest() {
		Theater theater = new Theater();
		List<ShowDetails> showDetailsList = new ArrayList<>();
		ShowDetails showDetails = new ShowDetails();
		showDetails.setLocalDateTime(LocalDateTime.now());
		showDetailsList.add(showDetails);
		Optional<Theater> optionalTheater = Optional.of(theater);
		when(theaterRepositoryMock.findById(1)).thenReturn(optionalTheater);
		Movie movie = new Movie();
		when(movieRepositoryMock.findByName("mock movie")).thenReturn(movie);
		when(showDetailsRepositoryMock.findByTheaterAndMovie(theater, movie)).thenReturn(showDetailsList);
		assertNotNull(showDetailsService.getShowDetailsList(1, "mock movie"));
	}
	
}
