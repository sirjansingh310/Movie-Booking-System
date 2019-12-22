package com.epam.moviebookingsystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.repository.MovieRepository;
import com.epam.moviebookingsystem.repository.ShowDetailsRepository;
import com.epam.moviebookingsystem.repository.TheaterRepository;


@Service
public class ShowDetailsService {
	@Autowired
	private ShowDetailsRepository showDetailsRepository;
	@Autowired
	private TheaterRepository theaterRepository;
	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public List<ShowDetails> getShowDetailsList(Integer theaterId, String movieName) {
		Optional<Theater> optionalTheater = theaterRepository.findById(theaterId);
		Movie movie = movieRepository.findByName(movieName);
		Theater theater;
		List<ShowDetails> showDetails = Arrays.asList();
		if (optionalTheater.isPresent()) {
			theater = optionalTheater.get();
			showDetails = showDetailsRepository.findByTheaterAndMovie(theater, movie);
			showDetails = filterShowDetails(showDetails, theater, movie);
		}
		return showDetails;
	}
	
	public List<ShowDetails> filterShowDetails(List<ShowDetails> allShowDetails, Theater theater, Movie movie) {
		List<ShowDetails> filteredShowDetails = new ArrayList<>();
		LocalDateTime currentTime = LocalDateTime.now();
			for (ShowDetails showDetails : allShowDetails) {
				if ((showDetails.getLocalDateTime().isAfter(currentTime)
						|| showDetails.getLocalDateTime().isEqual(currentTime)) 
						&& (showDetails.getLocalDateTime().toLocalDate().isBefore(LocalDate.now().plusDays(3)))) {
						filteredShowDetails.add(showDetails);
				}
		}
		return filteredShowDetails;
	}
}
