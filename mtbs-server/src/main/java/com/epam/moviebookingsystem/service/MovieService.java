package com.epam.moviebookingsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.MovieInLocation;
import com.epam.moviebookingsystem.repository.LocationRepository;
import com.epam.moviebookingsystem.repository.MovieInLocationRepository;
import com.epam.moviebookingsystem.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private MovieInLocationRepository movieInLocationRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Transactional(readOnly = true)
	public List<Movie> getMoviesByLocation(String cityName) {
		Location location = locationRepository.findByCityName(cityName);
		List<Movie> moviesInLocationList = new ArrayList<>();
		movieInLocationRepository.findByLocation(location).forEach(item -> moviesInLocationList.add(item.getMovie()));
		return moviesInLocationList;
	}
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAllMovies() {
		List<Movie> movies = (List<Movie>) movieRepository.findAll();
		List<Map<String, Object>> moviesWithLocations = new ArrayList<>();
		for(Movie movie : movies) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", movie.getId());
			map.put("name",movie.getName());
			List<MovieInLocation> movieInLocationList = movieInLocationRepository.findByMovie(movie);
			List<Location> locationList = new ArrayList<>();
			for(MovieInLocation movieInLocation: movieInLocationList) {
				locationList.add(movieInLocation.getLocation());
			}
			map.put("locationList", locationList);
			moviesWithLocations.add(map);
		}
		return moviesWithLocations;
	}
	@Transactional
	public void addMovie(String name, Integer[] locationIds) {
		Movie movie = new Movie();
		movie.setName(name);
		movieRepository.save(movie);
		for(Integer id: locationIds) {
			Location location = new Location();
			location.setId(id);
			MovieInLocation movieInLocation = new MovieInLocation();
			movieInLocation.setLocation(location);
			movieInLocation.setMovie(movie);
			movieInLocationRepository.save(movieInLocation);
		}
	}
	@Transactional
	public void deleteMovie(Integer movieId) {
		Movie movie = new Movie();
		movie.setId(movieId);
		movieRepository.delete(movie);
	}
	@Transactional
	public void editMovie(Integer movieId, String name, Integer[] locationIds) {
		Movie movie = new Movie();
		movie.setId(movieId);
		movie.setName(name);
		movieRepository.save(movie);
		movieInLocationRepository.deleteByMovie(movie);
		for(Integer id: locationIds) {
			MovieInLocation movieInLocation = new MovieInLocation();
			movieInLocation.setMovie(movie);
			Location location = new Location();
			location.setId(id);
			movieInLocation.setLocation(location);
			movieInLocationRepository.save(movieInLocation);
		}
	}
}
