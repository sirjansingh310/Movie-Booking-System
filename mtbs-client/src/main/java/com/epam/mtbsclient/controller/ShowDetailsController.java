package com.epam.mtbsclient.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.ShowDetailsDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.exception.ShowDetailsNotFoundException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class ShowDetailsController {
	@Autowired
	private RestClientService restClient;
	private Logger logger = LogManager.getLogger(ShowDetailsController.class);
	
	@PostMapping("showDetails")
	public ModelAndView getShowDetailsList(@RequestParam Integer userTheaterId, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		String movieName = (String)httpSession.getAttribute("userMovie");
		try {
			ResponseEntity<List<ShowDetailsDTO>> responseEntity = restClient.getShowDetails(userTheaterId, movieName);
			if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				throw new ShowDetailsNotFoundException();
			}
			else if(responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			}
			else {
				modelAndView.addObject("showDetailsList", responseEntity.getBody());
				modelAndView.setViewName("showDetails");
				httpSession.setAttribute("userTheaterId", userTheaterId);
			}
		}
		catch(ShowDetailsNotFoundException e) {
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
