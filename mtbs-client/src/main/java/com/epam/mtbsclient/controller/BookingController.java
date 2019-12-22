package com.epam.mtbsclient.controller;

import java.security.Principal;
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

import com.epam.mtbsclient.dto.SeatLayoutDTO;
import com.epam.mtbsclient.dto.SeatTypeDTO;
import com.epam.mtbsclient.dto.TicketDTO;
import com.epam.mtbsclient.exception.BookingFailedException;
import com.epam.mtbsclient.exception.BookingNotFoundException;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class BookingController {
	@Autowired
	private RestClientService restClient;
	private Logger logger = LogManager.getLogger(BookingController.class);
	private String redirectToErrorPage = "redirect:error";

	@PostMapping("seatLayout")
	public ModelAndView getSeatLayout(@RequestParam Integer userShowDetailsId, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ResponseEntity<SeatLayoutDTO> responseEntity = restClient.getSeatLayout(userShowDetailsId);
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				throw new BookingNotFoundException(userShowDetailsId);
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				modelAndView.addObject("seatLayout", responseEntity.getBody());
				modelAndView.setViewName("bookseats");
				httpSession.setAttribute("userShowDetailsId", userShowDetailsId);
				httpSession.setAttribute("seatTypeList", responseEntity.getBody().getSeatTypeList());
			}
		} catch (BookingNotFoundException e) {
			modelAndView.addObject("message", e.getMessage());
			modelAndView.setViewName("redirect:pageNotFound");
			logger.error(e.getMessage());
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToErrorPage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToErrorPage);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping("printTicket")
	public ModelAndView bookTicket(@RequestParam String seats[], HttpSession httpSession, Principal principal) {
		ModelAndView modelAndView;
		if ((boolean) httpSession.getAttribute("isTicketPrinted")) {
			modelAndView = (ModelAndView) httpSession.getAttribute("userTicket");
			modelAndView.addObject("isEmailSent", true);
		} else {
			modelAndView = bookForFirstTime(seats, httpSession, principal);
		}
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	private ModelAndView bookForFirstTime(String[] seats, HttpSession httpSession, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		Integer userShowDetailsId = (Integer) httpSession.getAttribute("userShowDetailsId");
		try {
			ResponseEntity<TicketDTO> responseEntity = restClient.bookTicket(userShowDetailsId, principal.getName(),
					seats);
			if (responseEntity.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				throw new BookingFailedException(seats, userShowDetailsId);
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			} else {
				TicketDTO ticket = responseEntity.getBody();
				modelAndView.addObject("ticket", ticket);
				modelAndView.addObject("ticketCount", seats.length);
				modelAndView.setViewName("ticket");
				String email = ticket.getUser().getEmail();
				modelAndView.addObject("seats", createSeatsString(ticket.getSeats(),
						(List<SeatTypeDTO>) httpSession.getAttribute("seatTypeList")));
				modelAndView.addObject("userEmail", email);
				modelAndView.addObject("username", principal.getName());
				modelAndView.addObject("isEmailSent", false);
				httpSession.setAttribute("isTicketPrinted", true);
				httpSession.setAttribute("userTicket", modelAndView);
			}
		} catch (BookingFailedException e) {
			modelAndView.addObject("message", e.getMessage());
			modelAndView.setViewName(redirectToErrorPage);
			logger.error(e.getMessage());
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToErrorPage);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToErrorPage);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	private String createSeatsString(String seats, List<SeatTypeDTO> seatTypeList) {
		StringBuilder stringBuilder = new StringBuilder();
		boolean isCategoryPrinted;
		for (SeatTypeDTO seatType : seatTypeList) {
			isCategoryPrinted = false;
			for (String seat : seats.split(" ")) {
				if (seat.contains(seatType.getId().toString())) {
					if (!isCategoryPrinted) {
						stringBuilder.append(" ").append(seatType.getCategory()).append(": ");
						isCategoryPrinted = true;
					}
					stringBuilder.append(seat.substring(0, seat.indexOf('-'))).append(" ");
				}
			}
		}
		return stringBuilder.toString();
	}
}
