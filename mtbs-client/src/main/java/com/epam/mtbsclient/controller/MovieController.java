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

import com.epam.mtbsclient.dto.MovieDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.exception.MovieNotFoundException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class MovieController {
	@Autowired
	private RestClientService restClient;
	private Logger logger = LogManager.getLogger(MovieController.class);

	@GetMapping("selectMovie")
	public ModelAndView getAllMoviesInUserLocation(@RequestParam String userLocation, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<MovieDTO>> responseEntity = restClient.getAllMoviesInLocation(userLocation);
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				throw new MovieNotFoundException(userLocation);
			}
			else if(responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			}
			else {
				modelAndView.addObject("moviesInUserLocation", responseEntity.getBody());
				modelAndView.setViewName("movies");
				httpSession.setAttribute("isTicketPrinted", false);
				httpSession.setAttribute("userLocation", userLocation);
			}
		}
		catch(MovieNotFoundException e) {
			modelAndView.addObject("message", e.getMessage());
			modelAndView.setViewName("redirect:pageNotFound");
			logger.error(e.getMessage());
		}
		catch(InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("redirect:error");
			logger.error(e.getMessage());
		}
		catch(Exception e) {
			modelAndView.setViewName("redirect:error");
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
}
