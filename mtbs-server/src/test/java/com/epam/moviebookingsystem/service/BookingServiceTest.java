package com.epam.moviebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.moviebookingsystem.entity.Booking;
import com.epam.moviebookingsystem.entity.SeatType;
import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.repository.BookingRepository;
import com.epam.moviebookingsystem.repository.SeatTypeRepository;
import com.epam.moviebookingsystem.repository.ShowDetailsRepository;
import com.epam.moviebookingsystem.repository.UserRepository;

class BookingServiceTest {

	@Mock
	private ShowDetailsRepository showDetailsRepositoryMock;
	@Mock
	private SeatTypeRepository seatTypeRepositoryMock;
	@Mock
	private BookingRepository bookingRepositoryMock;
	@Mock
	private UserRepository userRepositoryMock;
	@InjectMocks
	private BookingService bookingService;
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void getSeatLayoutTest() {
		ShowDetails showDetails = new ShowDetails();
		Optional<ShowDetails> optionalShowDetails = Optional.of(showDetails);
		when(showDetailsRepositoryMock.findById(1)).thenReturn(optionalShowDetails);
		List<SeatType> seatTypeList = new ArrayList<>();
		Theater theater = new Theater();
		when(seatTypeRepositoryMock.findByTheater(theater)).thenReturn(seatTypeList);
		Booking booking = new Booking();
		booking.setSeats("A1-1 A2-1 A3-1");
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking);
		when(bookingRepositoryMock.findByShowDetails(showDetails)).thenReturn(bookingList);
		Map<Object, Object> seatLayoutData = new HashMap<>();
		List<String> bookedSeats = new ArrayList<>();
		bookedSeats.add("A1-1");
		bookedSeats.add("A2-1");
		bookedSeats.add("A3-1");
		seatLayoutData.put("seatTypeList", seatTypeList);
		seatLayoutData.put("bookedSeats", bookedSeats);
		assertEquals(seatLayoutData, bookingService.getSeatLayout(1));
	}
	
	@Test
	void bookTicketTest() {
		User user = new User();
		Optional<User> optionalUser = Optional.of(user);
		user.setUsername("mockuser");
		when(userRepositoryMock.findById("mockuser")).thenReturn(optionalUser);
		Booking booking = new Booking();
		String seats = "A1-1 A2-1 A3-1 ";
		booking.setSeats(seats);
		Optional<Booking> optionalBooking = Optional.empty();
		ShowDetails showDetails = new ShowDetails();
		booking.setShowDetails(showDetails);
		Optional<ShowDetails> optionalShowDetails = Optional.of(showDetails);
		when(showDetailsRepositoryMock.findById(1)).thenReturn(optionalShowDetails);
		when(bookingRepositoryMock.save(booking)).thenReturn(booking);
		String inputSeats[] = {"A1-1", "A2-1", "A3-1"};
		assertEquals(optionalBooking, bookingService.bookTickets(1, "mock user", inputSeats));
	}
	
}
