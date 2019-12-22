package com.epam.moviebookingsystem.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.moviebookingsystem.entity.SeatType;
import com.epam.moviebookingsystem.service.SeatTypeService;

@Controller
public class SeatTypeController {
	@Autowired
	private SeatTypeService seatTypeService;
	private Logger logger = LogManager.getLogger(SeatTypeController.class);

	@GetMapping("seatType")
	public ResponseEntity<List<SeatType>> getAllSeatTypes() {
		ResponseEntity<List<SeatType>> responseEntity;
		List<SeatType> seatTypeList;
		try {
			seatTypeList = seatTypeService.getAllSeatTypes();
			if (seatTypeList.isEmpty())
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				responseEntity = new ResponseEntity<>(seatTypeList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@PostMapping("/seatType")
	public ResponseEntity<String> addSeatType(@RequestBody SeatType seatType) {
		ResponseEntity<String> responseEntity;
		try {
			seatTypeService.addSeatType(seatType);
			responseEntity = new ResponseEntity<>("SeatType Added", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("seatType/{seatTypeId}")
	public ResponseEntity<String> deleteSeatType(@PathVariable Integer seatTypeId) {
		ResponseEntity<String> responseEntity;
		try {
			seatTypeService.deleteSeatType(seatTypeId);
			responseEntity = new ResponseEntity<>("SeatType deleted", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
}
