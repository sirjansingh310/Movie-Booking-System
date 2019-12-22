package com.epam.moviebookingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Movie;
@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>{
	Movie findByName(String name);
}
