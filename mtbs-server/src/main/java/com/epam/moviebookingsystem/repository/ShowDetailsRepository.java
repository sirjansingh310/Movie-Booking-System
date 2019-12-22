package com.epam.moviebookingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.entity.Theater;
@Repository
public interface ShowDetailsRepository extends CrudRepository<ShowDetails, Integer> {
	List<ShowDetails> findByTheaterAndMovie(Theater theater, Movie movie);
}
