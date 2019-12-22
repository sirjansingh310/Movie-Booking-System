package com.epam.moviebookingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.Booking;
import com.epam.moviebookingsystem.entity.ShowDetails;
@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer>{
	List<Booking> findByShowDetails(ShowDetails showDetails);
}
