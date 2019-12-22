package com.epam.mtbsclient.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.LocationDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class LocationController {
	@Autowired
	private RestClientService restClient;
	private Logger logger = LogManager.getLogger(LocationController.class);

	@GetMapping("selectLocation")
	public ModelAndView getAllLocations() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<LocationDTO>> responseEntity = restClient.getAllLocations();
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("allLocations", responseEntity.getBody());
				modelAndView.setViewName("locations");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("redirect:error");
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName("redirect:error");
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
}
