package com.epam.moviebookingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Location;
@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{
	Location findByCityName(String cityName);
}
