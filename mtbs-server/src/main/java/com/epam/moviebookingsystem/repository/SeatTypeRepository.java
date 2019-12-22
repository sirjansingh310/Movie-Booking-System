package com.epam.moviebookingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.moviebookingsystem.entity.SeatType;
import com.epam.moviebookingsystem.entity.Theater;
@Repository
public interface SeatTypeRepository extends CrudRepository<SeatType,Integer>{
	List<SeatType> findByTheater(Theater theater);
}
