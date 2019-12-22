package com.epam.moviebookingsystem.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.moviebookingsystem.entity.Booking;
import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.service.BookingService;
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	BookingService bookingServiceMock;
	private String getUrl = "/booking/1";
	private String bookTicketUrl = "/booking/1/mockuser";
	@Test
	void getBookingDetailsTest() throws Exception{
		List<String> seats = new ArrayList<>();
		seats.add("A1-1");
		seats.add("A2-1");
		seats.add("A3-1");
		Map<Object, Object> seatLayoutData = new HashMap<>();
		seatLayoutData.put("rowCount", 10);
		seatLayoutData.put("columnCount", 10);
		seatLayoutData.put("bookedSeats", seats);
		when(bookingServiceMock.getSeatLayout(1)).thenReturn(seatLayoutData);
		mockMvc.perform(get(getUrl))
		.andExpect(status().isOk());
	}
	@Test
	void getEmptyBookingDetailsTest() throws Exception{
		Map<Object, Object> seatLayoutData = new HashMap<>();
		when(bookingServiceMock.getSeatLayout(1)).thenReturn(seatLayoutData);
		mockMvc.perform(get(getUrl))
		.andExpect(status().isNoContent());
	}
	@Test
	void exceptionOccuredInServiceLayerTest() throws Exception{
		when(bookingServiceMock.getSeatLayout(1)).thenThrow(new RuntimeException());
		mockMvc.perform(get(getUrl))
		.andExpect(status().isInternalServerError());
	}	
	
	@Test
	void bookTicketTest() throws Exception{
		String requestBodyJSON = "[\"D1-1\",\"D2-1\"]";
		Booking booking = new Booking();
		booking.setSeats("D1-1 D2-1");
		booking.setUser(new User());
		booking.setAmountPaid(300.0);
		booking.setBookingTimestamp(LocalDateTime.now());
		Optional<Booking> optionalBooking = Optional.of(booking);
		String seats[] = {"D1-1","D2-1"};
		when(bookingServiceMock.bookTickets(1, "mockuser", seats)).thenReturn(optionalBooking);
		mockMvc.perform(post(bookTicketUrl)
				.contentType("application/json")
				.content(requestBodyJSON))
		.andExpect(status().isOk());
	}

	@Test
	void bookTicketFailedTest() throws Exception{
		String seats[] = {"D1-1", "D2-1"};
		String requestBodyJSON = "[\"D1-1\",\"D2-1\"]";
		Optional<Booking> optionalBooking = Optional.empty();
		when(bookingServiceMock.bookTickets(1, "mockuser", seats)).thenReturn(optionalBooking);
		mockMvc.perform(post(bookTicketUrl)
				.contentType("application/json")
				.content(requestBodyJSON)
				)
		.andExpect(status().isServiceUnavailable());
	}
	@Test
	void serviceLayerExceptionTest() throws Exception{
		String requestBodyJSON = "[\"D1-1\",\"D2-1\"]";
		String seats[] = {"D1-1","D2-1"};
		when(bookingServiceMock.bookTickets(1, "mockuser",seats)).thenThrow(new RuntimeException());
		mockMvc.perform(post(bookTicketUrl)
				.contentType("application/json")
				.content(requestBodyJSON))
		.andExpect(status().isInternalServerError());
	}
}
