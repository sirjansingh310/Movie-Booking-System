package com.epam.mtbsclient.aspect;

import java.time.format.DateTimeFormatter;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.TicketDTO;


@Aspect
@Component
public class EmailAspect {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@AfterReturning(value = "execution(* com.epam.mtbsclient.controller.BookingController.bookTicket(..))", returning = "modelAndView")
	public void sendEmail(ModelAndView modelAndView) {
		if(modelAndView.getViewName().equals("redirect:error") || (boolean)modelAndView.getModelMap().get("isEmailSent"))
			return;
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	    simpleMailMessage.setSubject("Booking confirmation with bookmymovie");
	    StringBuilder stringBuilder = new StringBuilder();
	    TicketDTO ticket = (TicketDTO) modelAndView.getModelMap().get("ticket");
	    stringBuilder.append("Greetings from BookMyMovie, ")
	    .append((String)modelAndView.getModelMap().get("username"))
	    .append("\nHere are your ticket details")
	    .append("Movie: ")
	    .append(ticket.getShowDetails().getMovie().getName())
	    .append("\nBooking ID: ")
	    .append(ticket.getId())
	    .append("\nDate and time: ")
	    .append(ticket.getShowDetails().getLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy 'at' hh:mm a")))
	    .append("\nVenue: ")
	    .append(ticket.getShowDetails().getTheater().getName())
	    .append(", ")
	    .append(ticket.getShowDetails().getTheater().getLocation().getCityName())
	    .append("\nSeats: ")
	    .append((String)modelAndView.getModelMap().get("seats"))
	    .append("\nTotal including 18% GST Rs. ")
	    .append(String.format("%.2f", ticket.getAmountPaid()));
	    simpleMailMessage.setText(stringBuilder.toString());
	    simpleMailMessage.setTo((String)modelAndView.getModelMap().get("userEmail"));
	    javaMailSender.send(simpleMailMessage);
	}
}
