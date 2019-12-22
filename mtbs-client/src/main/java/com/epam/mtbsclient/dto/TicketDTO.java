package com.epam.mtbsclient.dto;

import java.time.LocalDateTime;

public class TicketDTO {
	private Integer id;
	private String seats;
	private ShowDetailsDTO showDetails;
	private LocalDateTime bookingTimestamp;
	private UserDTO user;
	private Double amountPaid;
	
	public LocalDateTime getBookingTimestamp() {
		return bookingTimestamp;
	}
	public void setBookingTimestamp(LocalDateTime bookingTimestamp) {
		this.bookingTimestamp = bookingTimestamp;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeats() {
		return seats;
	}
	public void setSeats(String seats) {
		this.seats = seats;
	}
	public ShowDetailsDTO getShowDetails() {
		return showDetails;
	}
	public void setShowDetails(ShowDetailsDTO showDetails) {
		this.showDetails = showDetails;
	}
	@Override
	public String toString() {
		return "TicketDTO [id=" + id + ", seats=" + seats + ", showDetails=" + showDetails + ", bookingTimestamp="
				+ bookingTimestamp + ", user=" + user + ", amountPaid=" + amountPaid + "]";
	}
	
	
}
