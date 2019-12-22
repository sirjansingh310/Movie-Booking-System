package com.epam.mtbsclient.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.TheaterDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.exception.TheaterNotFoundException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class TheaterController {
	private Logger logger = LogManager.getLogger(TheaterController.class);
	@Autowired
	private RestClientService restClient;

	@GetMapping("selectTheater")
	public ModelAndView getTheatersList(@RequestParam String userMovie, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		String userLocation = (String) httpSession.getAttribute("userLocation");
		try {
			ResponseEntity<List<TheaterDTO>> responseEntity = restClient.getTheatersList(userLocation, userMovie);
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				throw new TheaterNotFoundException(userMovie);
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("theaters", responseEntity.getBody());
				modelAndView.setViewName("theaters");
				httpSession.setAttribute("userMovie", userMovie);
			}
		} catch (TheaterNotFoundException e) {
			modelAndView.addObject("message", e.getMessage());
			modelAndView.setViewName("redirect:pageNotFound");
			logger.error(e.getMessage());
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
