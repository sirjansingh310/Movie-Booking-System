package com.epam.mtbsclient.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.LocationDTO;
import com.epam.mtbsclient.dto.MovieDTO;
import com.epam.mtbsclient.dto.SeatTypeDTO;
import com.epam.mtbsclient.dto.TheaterDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private RestClientService restClient;
	private Logger logger = LogManager.getLogger(AdminController.class);
	private String redirectToError = "redirect:error";

	@GetMapping(value = { "/", "" })
	public String adminHomePage() {
		return "redirect:/admin/location";
	}

	@GetMapping("/location")
	public ModelAndView getAllLocations() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<LocationDTO>> responseEntity = restClient.getAllLocations();
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("allLocations", responseEntity.getBody());
				modelAndView.setViewName("location");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("/addLocation")
	public ModelAndView addLocation(@RequestParam String cityName) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.addLocation(cityName);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/location");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/editLocation")
	public ModelAndView editLocation(@RequestParam Integer locationId, @RequestParam String cityName) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.editLocation(locationId, cityName);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/location");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/deleteLocation")
	public ModelAndView deleteLocation(@RequestParam Integer locationId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.deleteLocation(locationId);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/location");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/movie")
	public ModelAndView getAllMovies() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<MovieDTO>> responseEntity = restClient.getAllMovies();
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("allMovies", responseEntity.getBody());
				List<LocationDTO> allLocations = restClient.getAllLocations().getBody();
				modelAndView.addObject("allLocations", allLocations);
				modelAndView.setViewName("movie");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("/addMovie")
	public ModelAndView addNewMovie(@RequestParam String name, @RequestParam Integer[] locationId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.addMovie(name, locationId);
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			} else {
				modelAndView.setViewName("redirect:/admin/movie");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	@PostMapping("/editMovie")
	public ModelAndView editMovie(@RequestParam Integer movieId, @RequestParam String name, @RequestParam Integer[] locationId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.editMovie(movieId, name, locationId);
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			} else {
				modelAndView.setViewName("redirect:/admin/movie");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	@GetMapping("/deleteMovie")
	public ModelAndView deleteMovie(@RequestParam Integer movieId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.deleteMovie(movieId);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/movie");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/theater")
	public ModelAndView getAllTheaters() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<TheaterDTO>> responseEntity = restClient.getAllTheaters();
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("allTheaters", responseEntity.getBody());
				List<LocationDTO> allLocations = restClient.getAllLocations().getBody();
				modelAndView.addObject("allLocations", allLocations);
				modelAndView.setViewName("theater");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("addTheater")
	public ModelAndView addTheater(@RequestParam String name, Integer locationId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.addTheater(name, locationId);
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			} else {
				modelAndView.setViewName("redirect:/admin/theater");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/deleteTheater")
	public ModelAndView deleteTheater(@RequestParam Integer theaterId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.deleteTheater(theaterId);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/theater");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/seatType")
	public ModelAndView getAllSeatTypes() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<List<SeatTypeDTO>> responseEntity = restClient.getAllSeatTypes();
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("allSeatTypes", responseEntity.getBody());
				modelAndView.addObject("allTheaters", restClient.getAllTheaters().getBody());
				modelAndView.setViewName("seatType");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("addSeatType")
	public ModelAndView addTheater(@RequestParam String category, @RequestParam Integer numberOfRows,
			@RequestParam Integer numberOfColumns, @RequestParam Integer theaterId, @RequestParam Integer price) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			SeatTypeDTO seatType = new SeatTypeDTO();
			seatType.setCategory(category);
			seatType.setNumberOfRows(numberOfRows);
			seatType.setNumberOfColumns(numberOfColumns);
			seatType.setPrice(price);
			TheaterDTO theater = new TheaterDTO();
			theater.setId(theaterId);
			seatType.setTheater(theater);
			ResponseEntity<String> responseEntity = restClient.addSeatType(seatType);
			if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			} else {
				modelAndView.setViewName("redirect:/admin/seatType");
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/deleteSeatType")
	public ModelAndView deleteSeatType(@RequestParam Integer seatTypeId) {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "";
		try {
			ResponseEntity<String> responseEntity = restClient.deleteSeatType(seatTypeId);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				modelAndView.setViewName("redirect:/admin/seatType");
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorMessage = responseEntity.getBody();
				throw new InternalServerErrorException();
			}
		} catch (InternalServerErrorException e) {
			modelAndView.setViewName(redirectToError);
			modelAndView.addObject("errorMessage", errorMessage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
}
