package com.epam.moviebookingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.MovieInLocation;
@Repository
public interface MovieInLocationRepository extends CrudRepository<MovieInLocation, Integer>{
	List<MovieInLocation> findByLocation(Location location);
	List<MovieInLocation> findByMovie(Movie movie);
	Long deleteByMovie(Movie movie); 
}
