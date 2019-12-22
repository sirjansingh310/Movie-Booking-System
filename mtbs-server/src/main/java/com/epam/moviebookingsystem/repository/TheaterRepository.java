package com.epam.moviebookingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.Theater;
@Repository
public interface TheaterRepository extends CrudRepository<Theater, Integer> {
	List<Theater> fetchTheatersByLocationAndMovie(@Param("location") Location location, @Param("movie") Movie movie);

}
