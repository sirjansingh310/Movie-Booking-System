package com.epam.moviebookingsystem.controller;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.service.ShowDetailsService;

@Controller
public class ShowDetailsController {
	private Logger logger = LogManager.getLogger(ShowDetailsController.class);
	@Autowired
	private ShowDetailsService showDetailsService;

	@GetMapping("showdetails/{movieName}")
	public ResponseEntity<List<ShowDetails>> getShowDetails(@PathVariable String movieName,
			@RequestParam Integer theaterId) {
		ResponseEntity<List<ShowDetails>> responseEntity;
		List<ShowDetails> showDetails;
		try {
			showDetails = showDetailsService.getShowDetailsList(theaterId, movieName);
			if (showDetails.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				responseEntity = new ResponseEntity<>(showDetails, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
}
