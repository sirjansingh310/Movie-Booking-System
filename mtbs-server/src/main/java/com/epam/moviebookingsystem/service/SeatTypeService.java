package com.epam.moviebookingsystem.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.moviebookingsystem.entity.SeatType;
import com.epam.moviebookingsystem.repository.SeatTypeRepository;

@Service
public class SeatTypeService {
	@Autowired
	private SeatTypeRepository seatTypeRepository;
	@Transactional(readOnly = true)
	public List<SeatType> getAllSeatTypes() {
		return (List<SeatType>) seatTypeRepository.findAll();
	}
	@Transactional
	public void addSeatType(SeatType seatType) {
		seatTypeRepository.save(seatType);
	}
	@Transactional
	public void deleteSeatType(Integer seatTypeId) {
		seatTypeRepository.deleteById(seatTypeId);
	}
}
