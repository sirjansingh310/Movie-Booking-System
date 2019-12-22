package com.epam.moviebookingsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epam.moviebookingsystem.entity.Booking;
import com.epam.moviebookingsystem.entity.SeatType;
import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.repository.BookingRepository;
import com.epam.moviebookingsystem.repository.SeatTypeRepository;
import com.epam.moviebookingsystem.repository.ShowDetailsRepository;
import com.epam.moviebookingsystem.repository.UserRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ShowDetailsRepository showDetailsRepository;
	@Autowired
	private SeatTypeRepository seatTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Transactional(readOnly = true)
	public Map<Object, Object> getSeatLayout(Integer showId) {
		Optional<ShowDetails> optionalShowDetails = showDetailsRepository.findById(showId);
		ShowDetails showDetails;
		Map<Object, Object> seatLayoutData = new HashMap<>();
		List<String> bookedSeats = new ArrayList<>();
		if (optionalShowDetails.isPresent()) {
			showDetails = optionalShowDetails.get();
			Theater theater = showDetails.getTheater();
			List<SeatType> seatTypeList = seatTypeRepository.findByTheater(theater);
			seatLayoutData.put("seatTypeList",seatTypeList);
			List<Booking> userBookings = bookingRepository.findByShowDetails(showDetails);
			userBookings.stream().map(booking -> booking.getSeats())
					.forEach(s -> bookedSeats.addAll(Arrays.asList(s.split(" "))));
			seatLayoutData.put("bookedSeats", bookedSeats);
		}
		return seatLayoutData;
	}

	@Transactional
	public Optional<Booking> bookTickets(Integer showId, String username, String[] seats) {
		Optional<Booking> optionalBooking = Optional.empty();
		Optional<ShowDetails> optionalShowDetails = showDetailsRepository.findById(showId);
		if (optionalShowDetails.isPresent()) {
			ShowDetails showDetails = optionalShowDetails.get();
			Booking booking = new Booking();
			booking.setAmountPaid((getAmountPaidFromSeats(seats)));
			booking.setUser(getUserFromUsername(username));
			StringBuilder stringBuilder = new StringBuilder();
			Arrays.asList(seats).forEach(seat -> stringBuilder.append(seat).append(" "));
			booking.setSeats(stringBuilder.toString());
			booking.setShowDetails(showDetails);
			booking.setBookingTimestamp(LocalDateTime.now());
			booking = bookingRepository.save(booking);
			optionalBooking = Optional.ofNullable(booking);
		}
		return optionalBooking;
	}
	private Double getAmountPaidFromSeats(String[] seats) {
		Double totalAmountPaid = 0D;
		Integer seatTypeId;
		for(String seat: seats) {
			seatTypeId = Integer.parseInt(seat.substring(seat.indexOf('-') + 1, seat.length()));
			Optional<SeatType> seatType = seatTypeRepository.findById(seatTypeId);
			if(seatType.isPresent()) {
				totalAmountPaid += seatType.get().getPrice();
			}
		}
		totalAmountPaid *= 118;
		totalAmountPaid /= 100;
		return totalAmountPaid;
	}
	private User getUserFromUsername(String username) {
		User user = null;
		Optional<User> optionalUser = userRepository.findById(username); 
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		return user;
	}
}
