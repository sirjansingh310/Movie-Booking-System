package com.epam.moviebookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.repository.LocationRepository;
import com.epam.moviebookingsystem.repository.MovieRepository;
import com.epam.moviebookingsystem.repository.TheaterRepository;

@Service
public class TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private LocationRepository locationRepository;

	@Transactional(readOnly = true)
	public List<Theater> getTheatersList(String cityName, String userMovie) {
		Location location = locationRepository.findByCityName(cityName);
		Movie movie = movieRepository.findByName(userMovie);
		return theaterRepository.fetchTheatersByLocationAndMovie(location, movie);

	}
	@Transactional
	public List<Theater> getAllTheaters() {
		return (List<Theater>) theaterRepository.findAll();
	}
	@Transactional
	public void addTheater(String name, Integer locationId) {
		Location location = new Location();
		location.setId(locationId);
		Theater theater = new Theater();
		theater.setName(name);
		theater.setLocation(location);
		theaterRepository.save(theater);
	}
	@Transactional
	public void deleteTheater(Integer theaterId) {
		theaterRepository.deleteById(theaterId);
	}
}
